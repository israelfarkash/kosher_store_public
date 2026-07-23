#!/usr/bin/env python3
"""
מנהל החנות הפרטית - גרסת פרימיום (CustomTkinter)
אוטומציה מלאה ועיצוב מודרני.
"""

import json
import os
import shutil
import subprocess
import threading
import time
import urllib.parse
import zipfile
import hashlib
from pathlib import Path
import re

import customtkinter as ctk
from tkinter import filedialog, messagebox
from pyaxmlparser import APK
from PIL import Image

try:
    from google_play_scraper import app as play_scraper
except ImportError:
    play_scraper = None

# --- Configuration ---
ROOT_DIR = Path(__file__).resolve().parents[1]
APPS_JSON_PATH = ROOT_DIR / "apps.json"
ICONS_DIR = ROOT_DIR / "icons"

GITHUB_USER = "israelfarkash"
GITHUB_REPO = "kosher_store_public"
DEFAULT_GITHUB_BASE = f"https://raw.githubusercontent.com/{GITHUB_USER}/{GITHUB_REPO}/refs/heads/main"

CATEGORIES = [
    "פיננסים", "תחבורה", "אפליקציות גוגל", "מסרים",
    "כלים", "מדיה ובידור", "קניות", "לימוד וחינוך", "כללי",
]

# Theme configuration
ctk.set_appearance_mode("Dark")
ctk.set_default_color_theme("blue")

# --- Helper Functions ---

def file_size_mb(path: Path) -> str:
    size_mb = path.stat().st_size / (1024 * 1024)
    return f"{size_mb:.1f} MB"

def md5_checksum(path: Path) -> str:
    digest = hashlib.md5()
    with path.open("rb") as file:
        for chunk in iter(lambda: file.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()

def normalize_drive_url(value: str) -> str:
    value = value.strip()
    if not value:
        return value
    if "drive.google.com" not in value and "googleapis.com" not in value:
        return value
    parsed = urllib.parse.urlparse(value)
    file_id = None
    if "/file/d/" in parsed.path:
        file_id = parsed.path.split("/file/d/", 1)[1].split("/", 1)[0]
    elif "/files/" in parsed.path:
        file_id = parsed.path.split("/files/", 1)[1].split("?", 1)[0]
    else:
        file_id = urllib.parse.parse_qs(parsed.query).get("id", [None])[0]
    if not file_id:
        return value
    return f"https://drive.usercontent.google.com/download?id={file_id}&confirm=t&export=download"

def load_apps_json() -> list[dict]:
    if not APPS_JSON_PATH.exists():
        return []
    try:
        with APPS_JSON_PATH.open("r", encoding="utf-8") as f:
            return json.load(f)
    except Exception:
        return []

def save_apps_json(apps: list[dict]) -> None:
    with APPS_JSON_PATH.open("w", encoding="utf-8") as f:
        json.dump(apps, f, ensure_ascii=False, indent=2)

def extract_apk_info(apk_path: Path) -> dict:
    pkg, vcode, vname, name, icon = None, 0, "", None, None
    if apk_path.suffix.lower() == ".xapk":
        try:
            with zipfile.ZipFile(apk_path) as archive:
                if "manifest.json" in archive.namelist():
                    with archive.open("manifest.json") as f:
                        data = json.load(f)
                        pkg = data.get("package_name", "")
                        vcode = int(data.get("version_code", 0))
                        vname = data.get("version_name", "")
                        name = data.get("name", "")
                        for ext in [".png", ".webp"]:
                            if f"icon{ext}" in archive.namelist():
                                icon = f"icon{ext}"
                                break
        except Exception: pass

    if not pkg:
        try:
            apk = APK(str(apk_path))
            pkg = apk.get_package()
            vcode = int(apk.get_androidversion_code() or 0)
            vname = str(apk.get_androidversion_name() or vcode)
            name = apk.get_app_name() or pkg
            icon = apk.get_app_icon()
        except Exception as e:
            raise RuntimeError(f"שגיאה בקריאת APK: {e}")

    return {
        "apk_path": apk_path,
        "name": name or pkg,
        "package_name": pkg,
        "version_code": vcode,
        "version_name": vname or str(vcode),
        "size": file_size_mb(apk_path),
        "checksum": md5_checksum(apk_path),
        "icon_path_in_apk": icon
    }

# --- Main Application UI ---

class ModernStoreManager(ctk.CTk):
    def __init__(self):
        super().__init__()
        self.title("מנהל החנות - פרימיום")
        self.geometry("1050x700")
        self.minsize(900, 600)
        
        # Data state
        self.apps_data = load_apps_json()
        self.current_app_data = {}
        self.is_new_app = True
        
        # macOS clipboard shortcuts fix for customtkinter
        self.bind("<Command-c>", lambda e: self.event_generate("<<Copy>>"))
        self.bind("<Command-v>", lambda e: self.event_generate("<<Paste>>"))
        self.bind("<Command-x>", lambda e: self.event_generate("<<Cut>>"))
        self.bind("<Command-a>", lambda e: self.event_generate("<<SelectAll>>"))
        
        self.setup_ui()
        self.refresh_app_list()
        
    def setup_ui(self):
        # Configure grid layout (1 row, 2 columns)
        self.grid_rowconfigure(0, weight=1)
        self.grid_columnconfigure(1, weight=1)
        
        # --- Left Sidebar (App List) ---
        self.sidebar = ctk.CTkFrame(self, width=280, corner_radius=0)
        self.sidebar.grid(row=0, column=0, sticky="nsew")
        self.sidebar.grid_rowconfigure(3, weight=1)
        
        self.title_label = ctk.CTkLabel(self.sidebar, text="📱 החנות שלי", font=ctk.CTkFont(size=24, weight="bold"))
        self.title_label.grid(row=0, column=0, padx=20, pady=(30, 20))
        
        self.btn_load_apk = ctk.CTkButton(self.sidebar, text="הוסף אפליקציה חדשה (APK)", 
                                          command=self.load_new_apk,
                                          font=ctk.CTkFont(size=14, weight="bold"),
                                          height=40, fg_color="#2FA572", hover_color="#107C41")
        self.btn_load_apk.grid(row=1, column=0, padx=20, pady=10, sticky="ew")
        
        self.search_entry = ctk.CTkEntry(self.sidebar, placeholder_text="חיפוש...", height=35)
        self.search_entry.grid(row=2, column=0, padx=20, pady=10, sticky="ew")
        self.search_entry.bind("<KeyRelease>", self.filter_app_list)
        
        self.app_list_frame = ctk.CTkScrollableFrame(self.sidebar, fg_color="transparent")
        self.app_list_frame.grid(row=3, column=0, padx=10, pady=10, sticky="nsew")
        self.app_buttons = []
        
        # --- Right Main Content (Editor) ---
        self.main_content = ctk.CTkFrame(self, fg_color="transparent")
        self.main_content.grid(row=0, column=1, sticky="nsew", padx=20, pady=20)
        self.main_content.grid_columnconfigure(0, weight=1)
        self.main_content.grid_rowconfigure(2, weight=1)
        
        # Header Info Card
        self.info_card = ctk.CTkFrame(self.main_content, corner_radius=15)
        self.info_card.grid(row=0, column=0, sticky="ew", pady=(0, 20))
        self.info_card.grid_columnconfigure(1, weight=1)
        
        self.icon_label = ctk.CTkLabel(self.info_card, text="", width=80, height=80, corner_radius=15, fg_color="#333333")
        self.icon_label.grid(row=0, column=0, rowspan=3, padx=20, pady=20)
        
        self.lbl_app_name = ctk.CTkLabel(self.info_card, text="בחר או הוסף אפליקציה", font=ctk.CTkFont(size=22, weight="bold"))
        self.lbl_app_name.grid(row=0, column=1, sticky="w", padx=10, pady=(20, 0))
        
        self.lbl_app_pkg = ctk.CTkLabel(self.info_card, text="package.name", text_color="gray")
        self.lbl_app_pkg.grid(row=1, column=1, sticky="nw", padx=10)
        
        self.lbl_app_meta = ctk.CTkLabel(self.info_card, text="גרסה: -- | גודל: --", font=ctk.CTkFont(size=12))
        self.lbl_app_meta.grid(row=2, column=1, sticky="nw", padx=10, pady=(0, 20))
        
        self.btn_delete = ctk.CTkButton(self.info_card, text="מחק", width=60, fg_color="#D9534F", hover_color="#C9302C", command=self.delete_current_app)
        self.btn_delete.grid(row=0, column=2, padx=20, pady=20, sticky="ne")
        self.btn_delete.configure(state="disabled")
        
        # Forms Card (Scrollable)
        self.form_frame = ctk.CTkScrollableFrame(self.main_content, fg_color="transparent")
        self.form_frame.grid(row=1, column=0, sticky="nsew")
        self.form_frame.grid_columnconfigure(0, weight=1)
        
        # --- Form Fields ---
        def create_field(parent, label_text, placeholder="", has_paste=False):
            frame = ctk.CTkFrame(parent, fg_color="transparent")
            frame.pack(fill="x", pady=8)
            frame.grid_columnconfigure(0, weight=1)
            lbl = ctk.CTkLabel(frame, text=label_text, font=ctk.CTkFont(weight="bold"))
            lbl.grid(row=0, column=0, sticky="w", pady=(0, 2))
            
            entry_frame = ctk.CTkFrame(frame, fg_color="transparent")
            entry_frame.grid(row=1, column=0, sticky="ew")
            entry_frame.grid_columnconfigure(0, weight=1)
            
            entry = ctk.CTkEntry(entry_frame, placeholder_text=placeholder, height=38)
            entry.grid(row=0, column=0, sticky="ew")
            
            if has_paste:
                def paste_cmd():
                    try:
                        entry.delete(0, 'end')
                        entry.insert(0, self.clipboard_get())
                    except Exception:
                        pass
                btn_paste = ctk.CTkButton(entry_frame, text="הדבק 📥", width=60, height=38, command=paste_cmd, fg_color="#333333", hover_color="#444444")
                btn_paste.grid(row=0, column=1, padx=(10, 0))
                
            return entry

        self.entry_name = create_field(self.form_frame, "שם האפליקציה")
        self.entry_drive = create_field(self.form_frame, "🔗 קישור להורדה (Google Drive)", has_paste=True)
        
        # Category Dropdown
        cat_frame = ctk.CTkFrame(self.form_frame, fg_color="transparent")
        cat_frame.pack(fill="x", pady=8)
        cat_frame.grid_columnconfigure(0, weight=1)
        ctk.CTkLabel(cat_frame, text="קטגוריה", font=ctk.CTkFont(weight="bold")).grid(row=0, column=0, sticky="w")
        self.combo_category = ctk.CTkOptionMenu(cat_frame, values=CATEGORIES, height=38, font=ctk.CTkFont(size=13, weight="bold"))
        self.combo_category.grid(row=1, column=0, sticky="ew")
        
        # Description
        desc_frame = ctk.CTkFrame(self.form_frame, fg_color="transparent")
        desc_frame.pack(fill="x", pady=8)
        desc_frame.grid_columnconfigure(0, weight=1)
        ctk.CTkLabel(desc_frame, text="תיאור (מתמלא אוטומטית)", font=ctk.CTkFont(weight="bold")).grid(row=0, column=0, sticky="w")
        self.text_desc = ctk.CTkTextbox(desc_frame, height=120)
        self.text_desc.grid(row=1, column=0, sticky="ew")
        
        # Bottom Action Bar
        self.action_bar = ctk.CTkFrame(self.main_content, fg_color="transparent")
        self.action_bar.grid(row=3, column=0, sticky="ew", pady=(20, 0))
        self.action_bar.grid_columnconfigure(0, weight=1)
        self.action_bar.grid_columnconfigure(1, weight=1)
        
        self.status_label = ctk.CTkLabel(self.action_bar, text="מוכן לעבודה", text_color="gray")
        self.status_label.grid(row=0, column=0, sticky="w")
        
        self.btn_save_publish = ctk.CTkButton(self.action_bar, text="💾 שמור ופרסם", 
                                              command=self.save_and_publish,
                                              font=ctk.CTkFont(size=16, weight="bold"),
                                              height=50, fg_color="#1F6AA5", hover_color="#144870")
        self.btn_save_publish.grid(row=0, column=1, sticky="e")
        self.btn_save_publish.configure(state="disabled")

    def show_status(self, msg, is_error=False, is_success=False):
        color = "gray"
        if is_error: color = "#D9534F"
        elif is_success: color = "#2FA572"
        self.status_label.configure(text=msg, text_color=color)
        self.update_idletasks()

    def filter_app_list(self, event=None):
        query = self.search_entry.get().lower()
        for btn, app in self.app_buttons:
            if query in app.get("name", "").lower() or query in app.get("packageName", "").lower():
                btn.pack(fill="x", pady=2)
            else:
                btn.pack_forget()

    def refresh_app_list(self):
        for btn, _ in self.app_buttons:
            btn.destroy()
        self.app_buttons.clear()
        
        self.apps_data.sort(key=lambda x: x.get("name", "").lower())
        for app in self.apps_data:
            btn = ctk.CTkButton(self.app_list_frame, text=app.get("name", "Unknown"),
                                fg_color="transparent", text_color=("gray10", "gray90"),
                                hover_color=("gray70", "gray30"), anchor="w",
                                command=lambda a=app: self.load_app_to_form(a))
            btn.pack(fill="x", pady=2)
            self.app_buttons.append((btn, app))
        self.filter_app_list()

    def set_icon_image(self, image_path):
        try:
            if image_path and os.path.exists(image_path):
                img = Image.open(image_path)
                ctk_img = ctk.CTkImage(light_image=img, dark_image=img, size=(80, 80))
                self.icon_label.configure(image=ctk_img, text="")
            else:
                self.icon_label.configure(image=None, text="📦")
        except Exception:
            self.icon_label.configure(image=None, text="📦")

    def load_app_to_form(self, app):
        self.is_new_app = False
        self.current_app_data = app.copy()
        
        self.lbl_app_name.configure(text=app.get("name", ""))
        self.lbl_app_pkg.configure(text=app.get("packageName", ""))
        self.lbl_app_meta.configure(text=f"גרסה: {app.get('versionName', '')} | גודל: {app.get('size', '')}")
        self.set_icon_image(ICONS_DIR / f"{app.get('packageName')}.png")
        
        self.entry_name.delete(0, 'end')
        self.entry_name.insert(0, app.get("name", ""))
        
        self.entry_drive.delete(0, 'end')
        self.entry_drive.insert(0, app.get("apkUrl", ""))
        
        self.combo_category.set(app.get("category", "כללי"))
        
        self.text_desc.delete("1.0", "end")
        self.text_desc.insert("1.0", app.get("description", ""))
        
        self.btn_delete.configure(state="normal")
        self.btn_save_publish.configure(state="normal")
        self.show_status("אפליקציה נטענה בהצלחה.")

    def load_new_apk(self):
        apk_path = filedialog.askopenfilename(title="בחר קובץ APK", filetypes=[("APK Files", "*.apk *.xapk")])
        if not apk_path: return
        
        self.show_status("⏳ מעבד APK ושואב נתונים אוטומטית...", is_success=True)
        self.btn_save_publish.configure(state="disabled")
        
        def process():
            try:
                # 1. Parse APK
                meta = extract_apk_info(Path(apk_path))
                pkg = meta["package_name"]
                
                # Setup default data
                app_data = {
                    "name": meta["name"],
                    "packageName": pkg,
                    "versionCode": meta["version_code"],
                    "versionName": meta["version_name"],
                    "size": meta["size"],
                    "checksum": meta["checksum"],
                    "checksumType": "MD5",
                    "category": "כללי",
                    "description": "",
                    "apkUrl": "",
                    "iconUrl": f"{DEFAULT_GITHUB_BASE}/icons/{pkg}.png"
                }
                
                # 2. Try to fetch from Play Store automatically
                play_data = None
                if play_scraper:
                    try:
                        play_data = play_scraper(pkg, lang="iw", country="il")
                    except Exception:
                        pass
                
                # 3. Handle Icon
                ICONS_DIR.mkdir(exist_ok=True)
                icon_out_path = ICONS_DIR / f"{pkg}.png"
                icon_saved = False
                
                if play_data and play_data.get("icon"):
                    try:
                        import urllib.request
                        with urllib.request.urlopen(play_data["icon"]) as res:
                            with open(icon_out_path, "wb") as f:
                                f.write(res.read())
                        icon_saved = True
                    except Exception: pass
                
                if not icon_saved and meta["icon_path_in_apk"]:
                    try:
                        with zipfile.ZipFile(apk_path) as archive:
                            with archive.open(meta["icon_path_in_apk"]) as icon_file:
                                with open(icon_out_path, "wb") as f:
                                    shutil.copyfileobj(icon_file, f)
                    except Exception: pass

                # 4. Handle Description
                if play_data and play_data.get("description"):
                    app_data["description"] = play_data["description"]
                if play_data and play_data.get("screenshots"):
                    app_data["screenshots"] = play_data["screenshots"][:5]
                
                # Update UI
                def update_ui():
                    self.is_new_app = True
                    self.current_app_data = app_data
                    
                    self.lbl_app_name.configure(text=app_data["name"])
                    self.lbl_app_pkg.configure(text=app_data["packageName"])
                    self.lbl_app_meta.configure(text=f"גרסה: {app_data['versionName']} | גודל: {app_data['size']}")
                    self.set_icon_image(icon_out_path)
                    
                    self.entry_name.delete(0, 'end')
                    self.entry_name.insert(0, app_data["name"])
                    
                    self.entry_drive.delete(0, 'end')
                    
                    self.combo_category.set("כללי")
                    
                    self.text_desc.delete("1.0", "end")
                    self.text_desc.insert("1.0", app_data["description"])
                    
                    self.btn_delete.configure(state="disabled")
                    self.btn_save_publish.configure(state="normal")
                    self.show_status("✅ הנתונים נשאבו! הדבק קישור לדרייב ולחץ על 'שמור ופרסם'.", is_success=True)
                
                self.after(0, update_ui)
                
            except Exception as e:
                self.after(0, lambda: self.show_status(f"❌ שגיאה: {e}", is_error=True))
                
        threading.Thread(target=process, daemon=True).start()

    def delete_current_app(self):
        pkg = self.current_app_data.get("packageName")
        if not pkg: return
        
        # Custom confirmation dialog
        dialog = ctk.CTkToplevel(self)
        dialog.title("אישור מחיקה")
        dialog.geometry("300x150")
        dialog.transient(self)
        dialog.grab_set()
        
        ctk.CTkLabel(dialog, text=f"האם אתה בטוח שברצונך למחוק את\n{self.current_app_data.get('name')}?").pack(pady=20)
        
        btn_frame = ctk.CTkFrame(dialog, fg_color="transparent")
        btn_frame.pack(fill="x", padx=20)
        
        def confirm():
            name = self.current_app_data.get("name", pkg)
            self.apps_data = [a for a in self.apps_data if a.get("packageName") != pkg]
            save_apps_json(self.apps_data)
            icon_file = ICONS_DIR / f"{pkg}.png"
            if icon_file.exists(): icon_file.unlink()
            
            # Clear form & UI state
            self.current_app_data = {}
            self.entry_name.delete(0, 'end')
            self.entry_drive.delete(0, 'end')
            self.combo_category.set("כללי")
            self.text_desc.delete("1.0", "end")
            self.lbl_app_name.configure(text="בחר או הוסף אפליקציה")
            self.lbl_app_pkg.configure(text="")
            self.lbl_app_meta.configure(text="")
            self.set_icon_image(None)
            
            self.refresh_app_list()
            self.btn_save_publish.configure(state="disabled")
            self.btn_delete.configure(state="disabled")
            self.show_status(f"🗑️ '{name}' נמחקה ומפורסמת לענן...", is_error=True)
            dialog.destroy()
            
            # Auto push deletion to GitHub
            def push_deletion():
                try:
                    subprocess.run(["git", "add", "apps.json", "icons/"], cwd=ROOT_DIR, check=True)
                    res = subprocess.run(["git", "commit", "-m", f"Delete {name}"], cwd=ROOT_DIR, capture_output=True, text=True)
                    if "nothing to commit" not in res.stdout + res.stderr:
                        subprocess.run(["git", "push", "origin", "main"], cwd=ROOT_DIR, check=True)
                    self.after(0, lambda: self.show_status(f"🗑️ '{name}' נמחקה ופורסמה בהצלחה!", is_error=True))
                except Exception as e:
                    self.after(0, lambda: self.show_status(f"⚠️ נמחקה מקומית אך הפרסום נכשל: {e}", is_error=True))
                    
            threading.Thread(target=push_deletion, daemon=True).start()
            
        def cancel(): dialog.destroy()
        
        ctk.CTkButton(btn_frame, text="כן, מחק", fg_color="#D9534F", hover_color="#C9302C", command=confirm).pack(side="left", padx=5)
        ctk.CTkButton(btn_frame, text="ביטול", fg_color="gray", command=cancel).pack(side="right", padx=5)

    def save_and_publish(self):
        url = normalize_drive_url(self.entry_drive.get())
        name = self.entry_name.get().strip()
        
        if not url:
            self.show_status("❌ שגיאה: חובה להזין קישור להורדה (Google Drive).", is_error=True)
            return
        if not name:
            self.show_status("❌ שגיאה: חובה להזין שם אפליקציה.", is_error=True)
            return
            
        self.show_status("⏳ שומר ומפרסם אוטומטית ברקע...", is_success=True)
        self.btn_save_publish.configure(state="disabled")
        
        def process():
            try:
                # 1. Update data
                self.current_app_data["name"] = name
                self.current_app_data["apkUrl"] = url
                self.current_app_data["category"] = self.combo_category.get()
                self.current_app_data["description"] = self.text_desc.get("1.0", "end").strip()
                
                pkg = self.current_app_data["packageName"]
                
                # Upsert to list
                found = False
                for i, app in enumerate(self.apps_data):
                    if app.get("packageName") == pkg:
                        self.apps_data[i] = self.current_app_data
                        found = True
                        break
                if not found:
                    self.apps_data.append(self.current_app_data)
                    
                save_apps_json(self.apps_data)
                
                # 2. Git Push
                subprocess.run(["git", "add", "apps.json", "icons/"], cwd=ROOT_DIR, check=True)
                res = subprocess.run(["git", "commit", "-m", f"Automated update for {name}"], cwd=ROOT_DIR, capture_output=True, text=True)
                if "nothing to commit" not in res.stdout + res.stderr:
                    subprocess.run(["git", "push", "origin", "main"], cwd=ROOT_DIR, check=True)
                
                self.after(0, lambda: self.refresh_app_list())
                self.after(0, lambda: self.show_status(f"🎉 הושלם! '{name}' באוויר.", is_success=True))
                self.after(0, lambda: self.btn_save_publish.configure(state="normal"))
                
            except Exception as e:
                self.after(0, lambda: self.show_status(f"❌ שגיאה בשמירה/פרסום: {e}", is_error=True))
                self.after(0, lambda: self.btn_save_publish.configure(state="normal"))
                
        threading.Thread(target=process, daemon=True).start()

if __name__ == "__main__":
    app = ModernStoreManager()
    app.mainloop()
