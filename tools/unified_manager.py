#!/usr/bin/env python3
"""
מנהל החנות הפרטית - גרסת פרימיום (CustomTkinter)
אוטומציה מלאה, ממיר קישורי Google Drive ועיצוב מודרני.
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
import html
from pathlib import Path
import re
import webbrowser

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

def clean_description(text: str) -> str:
    if not text:
        return ""
    text = html.unescape(text)
    text = re.sub(r'<br\s*/?>', '\n', text, flags=re.IGNORECASE)
    text = re.sub(r'</?p\s*/?>', '\n', text, flags=re.IGNORECASE)
    text = re.sub(r'<[^>]+>', '', text)
    text = text.replace('\r\n', '\n').replace('\r', '\n')
    text = re.sub(r'\n{3,}', '\n\n', text)
    return text.strip()

# --- Google Drive Link Extractor & Converter Logic ---

def extract_drive_id(url: str) -> tuple[str | None, str]:
    """
    Extracts Google Drive ID safely using regular expressions and determines the item type ('file', 'folder', 'invalid', or 'empty').
    
    Supported formats:
    - https://drive.google.com/file/d/FILE_ID/view
    - https://drive.google.com/file/d/FILE_ID/view?usp=sharing
    - https://drive.google.com/open?id=FILE_ID
    - https://drive.google.com/uc?id=FILE_ID
    - https://docs.google.com/uc?id=FILE_ID
    - https://drive.google.com/drive/folders/FOLDER_ID
    """
    url = (url or "").strip()
    if not url:
        return None, "empty"
    
    # 1. Match Folder URL pattern: /folders/FOLDER_ID
    folder_match = re.search(r'/folders/([a-zA-Z0-9_-]+)', url)
    if folder_match:
        return folder_match.group(1), "folder"
        
    # 2. Match File URL patterns: /file/d/FILE_ID, id=FILE_ID, /files/FILE_ID, /uc?id=FILE_ID
    file_match = re.search(r'/file/d/([a-zA-Z0-9_-]+)', url) or \
                 re.search(r'[?&]id=([a-zA-Z0-9_-]+)', url) or \
                 re.search(r'/files/([a-zA-Z0-9_-]+)', url) or \
                 re.search(r'/uc\?id=([a-zA-Z0-9_-]+)', url)
                 
    if file_match:
        return file_match.group(1), "file"
        
    return None, "invalid"

def normalize_drive_url(value: str) -> str:
    """
    Converts any Google Drive link to the fast direct download URL bypassing virus scan warnings.
    """
    file_id, item_type = extract_drive_id(value)
    if item_type == "file" and file_id:
        return f"https://drive.usercontent.google.com/download?id={file_id}&confirm=t&export=download"
    return value.strip()

def generate_drive_links(url: str) -> dict:
    """
    Generates all available direct download, preview, view, and open links for a Google Drive URL.
    Returns a status dict containing formatted link options or friendly error messages.
    """
    file_id, item_type = extract_drive_id(url)
    
    if item_type == "empty":
        return {"status": "error", "message": "⚠️ אנא הזן קישור של Google Drive."}
    if item_type == "invalid" or not file_id:
        return {"status": "error", "message": "❌ קישור Google Drive אינו תקין או שלא ניתן היה לחלץ ממנו מזהה ID."}
        
    if item_type == "folder":
        folder_url = f"https://drive.google.com/drive/folders/{file_id}"
        return {
            "status": "success",
            "type": "folder",
            "id": file_id,
            "notice": "📁 הורדה ישירה אינה נתמכת בתיקיות ב-Google Drive. ניתן לגשת לצפייה בתיקייה בדפדפן.",
            "links": [
                {
                    "title": "📁 פתיחת תיקייה (Folder View)",
                    "desc": "פתיחת התיקייה ב-Google Drive בדפדפן.",
                    "url": folder_url
                }
            ]
        }
    
    # File link generation
    fast_direct = f"https://drive.usercontent.google.com/download?id={file_id}&confirm=t&export=download"
    standard_direct = f"https://drive.google.com/uc?export=download&id={file_id}"
    preview_url = f"https://drive.google.com/file/d/{file_id}/preview"
    view_url = f"https://drive.google.com/file/d/{file_id}/view"
    open_url = f"https://drive.google.com/open?id={file_id}"
    
    return {
        "status": "success",
        "type": "file",
        "id": file_id,
        "links": [
            {
                "title": "⚡ הורדה ישירה מהירה (Fast Direct Download)",
                "desc": "קישור הורדה ישיר ועוקף דף אזהרת וירוסים ב-Google Drive.",
                "url": fast_direct
            },
            {
                "title": "📥 הורדה ישירה רגילה (Direct Download)",
                "desc": "קישור הורדה ישיר תקני מ-Google Drive.",
                "url": standard_direct
            },
            {
                "title": "👁️ תצוגה מקדימה (Preview)",
                "desc": "צפייה מוקדמת בקובץ בתוך מציג מובנה בדפדפן.",
                "url": preview_url
            },
            {
                "title": "📄 צפייה בקובץ (View)",
                "desc": "פתיחת עמוד הקובץ הרגיל ב-Google Drive.",
                "url": view_url
            },
            {
                "title": "🔗 פתיחת מזהה (Open ID)",
                "desc": "פתיחת הקובץ באמצעות מזהה ה-ID.",
                "url": open_url
            }
        ]
    }

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
        self.title("מנהל החנות הפרטית & ממיר קישורים")
        self.geometry("1100x730")
        self.minsize(920, 620)
        
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
        
        # --- Left Sidebar ---
        self.sidebar = ctk.CTkFrame(self, width=280, corner_radius=0)
        self.sidebar.grid(row=0, column=0, sticky="nsew")
        self.sidebar.grid_rowconfigure(4, weight=1)
        
        self.title_label = ctk.CTkLabel(self.sidebar, text="📱 החנות שלי", font=ctk.CTkFont(size=22, weight="bold"))
        self.title_label.grid(row=0, column=0, padx=20, pady=(20, 10))
        
        # View Switching Navigation Buttons
        nav_frame = ctk.CTkFrame(self.sidebar, fg_color="transparent")
        nav_frame.grid(row=1, column=0, padx=15, pady=5, sticky="ew")
        nav_frame.grid_columnconfigure((0, 1), weight=1)
        
        self.btn_nav_store = ctk.CTkButton(nav_frame, text="🏪 ניהול חנות", height=35,
                                            font=ctk.CTkFont(size=12, weight="bold"),
                                            fg_color="#1F6AA5", hover_color="#144870",
                                            command=self.show_store_view)
        self.btn_nav_store.grid(row=0, column=0, padx=2, sticky="ew")
        
        self.btn_nav_converter = ctk.CTkButton(nav_frame, text="🔗 ממיר דרייב", height=35,
                                                font=ctk.CTkFont(size=12, weight="bold"),
                                                fg_color="#333344", hover_color="#444455",
                                                command=self.show_converter_view)
        self.btn_nav_converter.grid(row=0, column=1, padx=2, sticky="ew")
        
        self.btn_load_apk = ctk.CTkButton(self.sidebar, text="הוסף אפליקציה חדשה (APK)", 
                                          command=self.load_new_apk,
                                          font=ctk.CTkFont(size=13, weight="bold"),
                                          height=38, fg_color="#2FA572", hover_color="#107C41")
        self.btn_load_apk.grid(row=2, column=0, padx=15, pady=10, sticky="ew")
        
        self.search_entry = ctk.CTkEntry(self.sidebar, placeholder_text="חיפוש...", height=35)
        self.search_entry.grid(row=3, column=0, padx=15, pady=5, sticky="ew")
        self.search_entry.bind("<KeyRelease>", self.filter_app_list)
        
        self.app_list_frame = ctk.CTkScrollableFrame(self.sidebar, fg_color="transparent")
        self.app_list_frame.grid(row=4, column=0, padx=10, pady=10, sticky="nsew")
        self.app_buttons = []
        
        # --- Right Main Content Area ---
        self.main_content = ctk.CTkFrame(self, fg_color="transparent")
        self.main_content.grid(row=0, column=1, sticky="nsew", padx=20, pady=20)
        self.main_content.grid_columnconfigure(0, weight=1)
        self.main_content.grid_rowconfigure(0, weight=1)
        
        # --- View 1: App Store Manager Editor ---
        self.store_view_frame = ctk.CTkFrame(self.main_content, fg_color="transparent")
        self.store_view_frame.grid(row=0, column=0, sticky="nsew")
        self.store_view_frame.grid_columnconfigure(0, weight=1)
        self.store_view_frame.grid_rowconfigure(1, weight=1)
        
        # Header Info Card
        self.info_card = ctk.CTkFrame(self.store_view_frame, corner_radius=15)
        self.info_card.grid(row=0, column=0, sticky="ew", pady=(0, 15))
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
        self.form_frame = ctk.CTkScrollableFrame(self.store_view_frame, fg_color="transparent")
        self.form_frame.grid(row=1, column=0, sticky="nsew")
        self.form_frame.grid_columnconfigure(0, weight=1)
        
        # --- Form Fields ---
        def create_field(parent, label_text, placeholder="", has_paste=False):
            frame = ctk.CTkFrame(parent, fg_color="transparent")
            frame.pack(fill="x", pady=6)
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
                        val = self.clipboard_get().strip()
                        norm = normalize_drive_url(val)
                        entry.delete(0, 'end')
                        entry.insert(0, norm)
                        if norm != val and norm:
                            self.show_status("⚡ הקישור הומק אוטומטית להורדה מהירה!", is_success=True)
                    except Exception:
                        pass
                btn_paste = ctk.CTkButton(entry_frame, text="הדבק 📥", width=65, height=38, command=paste_cmd, fg_color="#333333", hover_color="#444444")
                btn_paste.grid(row=0, column=1, padx=(10, 0))
                
            return entry

        self.entry_name = create_field(self.form_frame, "שם האפליקציה")
        self.entry_drive = create_field(self.form_frame, "🔗 קישור להורדה (Google Drive)", has_paste=True)
        
        # Auto-convert Drive URL when user pastes or leaves focus
        def auto_convert_drive_input(event=None):
            val = self.entry_drive.get().strip()
            norm = normalize_drive_url(val)
            if norm != val and norm:
                self.entry_drive.delete(0, 'end')
                self.entry_drive.insert(0, norm)
                self.show_status("⚡ הקישור הומר אוטומטית להורדה מהירה!", is_success=True)
                
        self.entry_drive.bind("<FocusOut>", auto_convert_drive_input)
        self.entry_drive.bind("<KeyRelease>", auto_convert_drive_input)
        
        # Category Selector (Editable Entry + Quick Chips)
        cat_frame = ctk.CTkFrame(self.form_frame, fg_color="transparent")
        cat_frame.pack(fill="x", pady=6)
        cat_frame.grid_columnconfigure(0, weight=1)
        ctk.CTkLabel(cat_frame, text="קטגוריה (לחץ על כפתור או הקלד קטגוריה חדשה)", font=ctk.CTkFont(weight="bold")).grid(row=0, column=0, sticky="w", pady=(0, 2))
        
        self.entry_category = ctk.CTkEntry(cat_frame, placeholder_text="כללי", height=38)
        self.entry_category.grid(row=1, column=0, sticky="ew")
        
        chips_frame = ctk.CTkFrame(cat_frame, fg_color="transparent")
        chips_frame.grid(row=2, column=0, sticky="ew", pady=(6, 0))
        
        def select_chip(c_name):
            self.entry_category.delete(0, 'end')
            self.entry_category.insert(0, c_name)

        for i, cat_name in enumerate(CATEGORIES):
            btn = ctk.CTkButton(
                chips_frame, text=cat_name, height=30,
                font=ctk.CTkFont(size=11, weight="bold"),
                fg_color="#333344", hover_color="#1F6AA5",
                command=lambda c=cat_name: select_chip(c)
            )
            btn.grid(row=i // 5, column=i % 5, padx=2, pady=2, sticky="ew")
            chips_frame.grid_columnconfigure(i % 5, weight=1)

        class CategoryAdapter:
            def __init__(self, entry): self.entry = entry
            def set(self, val):
                self.entry.delete(0, 'end')
                self.entry.insert(0, val or "כללי")
            def get(self): return self.entry.get().strip() or "כללי"
            
        self.combo_category = CategoryAdapter(self.entry_category)
        
        # Description
        desc_frame = ctk.CTkFrame(self.form_frame, fg_color="transparent")
        desc_frame.pack(fill="x", pady=6)
        desc_frame.grid_columnconfigure(0, weight=1)
        ctk.CTkLabel(desc_frame, text="תיאור (מתמלא אוטומטית)", font=ctk.CTkFont(weight="bold")).grid(row=0, column=0, sticky="w")
        self.text_desc = ctk.CTkTextbox(desc_frame, height=110)
        self.text_desc.grid(row=1, column=0, sticky="ew")
        
        # Bottom Action Bar
        self.action_bar = ctk.CTkFrame(self.store_view_frame, fg_color="transparent")
        self.action_bar.grid(row=2, column=0, sticky="ew", pady=(15, 0))
        self.action_bar.grid_columnconfigure(0, weight=1)
        self.action_bar.grid_columnconfigure(1, weight=1)
        
        self.status_label = ctk.CTkLabel(self.action_bar, text="מוכן לעבודה", text_color="gray")
        self.status_label.grid(row=0, column=0, sticky="w")
        
        self.btn_save_publish = ctk.CTkButton(self.action_bar, text="💾 שמור ופרסם", 
                                              command=self.save_and_publish,
                                              font=ctk.CTkFont(size=15, weight="bold"),
                                              height=45, fg_color="#1F6AA5", hover_color="#144870")
        self.btn_save_publish.grid(row=0, column=1, sticky="e")
        self.btn_save_publish.configure(state="disabled")
        
        # --- View 2: Google Drive Link Converter Page ---
        self.converter_view_frame = ctk.CTkFrame(self.main_content, fg_color="transparent")
        self.converter_view_frame.grid_columnconfigure(0, weight=1)
        self.converter_view_frame.grid_rowconfigure(2, weight=1)
        
        # Converter Header
        conv_header = ctk.CTkFrame(self.converter_view_frame, corner_radius=15)
        conv_header.grid(row=0, column=0, sticky="ew", pady=(0, 15))
        
        ctk.CTkLabel(conv_header, text="🔗 ממיר קישורי Google Drive", 
                     font=ctk.CTkFont(size=22, weight="bold")).pack(anchor="w", padx=20, pady=(15, 5))
        ctk.CTkLabel(conv_header, text="המר בקלות קישורי Google Drive מכל סוג לקישורי הורדה ישירים, תצוגה מקדימה, פתיחה וצפייה.", 
                     text_color="gray", font=ctk.CTkFont(size=12)).pack(anchor="w", padx=20, pady=(0, 15))
                     
        # Converter Input Card
        conv_input_card = ctk.CTkFrame(self.converter_view_frame, corner_radius=15)
        conv_input_card.grid(row=1, column=0, sticky="ew", pady=(0, 15))
        conv_input_card.grid_columnconfigure(0, weight=1)
        
        ctk.CTkLabel(conv_input_card, text="הכנס קישור Google Drive לשיתוף:", 
                     font=ctk.CTkFont(weight="bold")).grid(row=0, column=0, sticky="w", padx=20, pady=(15, 5))
                     
        input_row = ctk.CTkFrame(conv_input_card, fg_color="transparent")
        input_row.grid(row=1, column=0, sticky="ew", padx=20, pady=(0, 15))
        input_row.grid_columnconfigure(0, weight=1)
        
        self.conv_entry_url = ctk.CTkEntry(input_row, placeholder_text="https://drive.google.com/file/d/.../view?usp=sharing", height=42)
        self.conv_entry_url.grid(row=0, column=0, sticky="ew", padx=(0, 10))
        
        btn_paste_conv = ctk.CTkButton(input_row, text="הדבק 📥", width=70, height=42, fg_color="#333333", hover_color="#444444",
                                       command=lambda: self.paste_to_converter())
        btn_paste_conv.grid(row=0, column=1, padx=(0, 8))
        
        btn_convert = ctk.CTkButton(input_row, text="המר ⚡", width=90, height=42,
                                     font=ctk.CTkFont(size=14, weight="bold"),
                                     fg_color="#1F6AA5", hover_color="#144870",
                                     command=self.run_converter)
        btn_convert.grid(row=0, column=2, padx=(0, 8))
        
        btn_clear = ctk.CTkButton(input_row, text="נקה 🧹", width=70, height=42,
                                   fg_color="#D9534F", hover_color="#C9302C",
                                   command=self.clear_converter)
        btn_clear.grid(row=0, column=3)
        
        # Converter Loading Progress
        self.conv_progress = ctk.CTkProgressBar(self.converter_view_frame, orientation="horizontal", height=4, mode="indeterminate")
        
        # Converter Results Container (Scrollable Cards)
        self.conv_results_frame = ctk.CTkScrollableFrame(self.converter_view_frame, fg_color="transparent")
        self.conv_results_frame.grid(row=2, column=0, sticky="nsew")
        self.conv_results_frame.grid_columnconfigure(0, weight=1)
        
        self.conv_status_label = ctk.CTkLabel(self.conv_results_frame, text="הזן קישור לחץ על 'המר ⚡' כדי ליצור קישורי הורדה ישירים.", text_color="gray")
        self.conv_status_label.pack(pady=40)

    # --- Navigation Between Views ---
    def show_store_view(self):
        self.converter_view_frame.grid_forget()
        self.store_view_frame.grid(row=0, column=0, sticky="nsew")
        self.btn_nav_store.configure(fg_color="#1F6AA5")
        self.btn_nav_converter.configure(fg_color="#333344")

    def show_converter_view(self):
        self.store_view_frame.grid_forget()
        self.converter_view_frame.grid(row=0, column=0, sticky="nsew")
        self.btn_nav_converter.configure(fg_color="#1F6AA5")
        self.btn_nav_store.configure(fg_color="#333344")

    # --- Converter Logic & UX ---
    def paste_to_converter(self):
        try:
            self.conv_entry_url.delete(0, 'end')
            self.conv_entry_url.insert(0, self.clipboard_get().strip())
        except Exception:
            pass

    def clear_converter(self):
        self.conv_entry_url.delete(0, 'end')
        for child in self.conv_results_frame.winfo_children():
            child.destroy()
        self.conv_status_label = ctk.CTkLabel(self.conv_results_frame, text="הזן קישור לחץ על 'המר ⚡' כדי ליצור קישורי הורדה ישירים.", text_color="gray")
        self.conv_status_label.pack(pady=40)

    def run_converter(self):
        url = self.conv_entry_url.get().strip()
        
        # Clear previous results
        for child in self.conv_results_frame.winfo_children():
            child.destroy()
            
        # 1. Validation
        if not url:
            err_card = ctk.CTkFrame(self.conv_results_frame, fg_color="#3A1D1D", corner_radius=10)
            err_card.pack(fill="x", pady=10, padx=5)
            ctk.CTkLabel(err_card, text="⚠️ שגיאה: אנא הזן קישור של Google Drive.", font=ctk.CTkFont(weight="bold"), text_color="#FF6B6B").pack(pady=15, padx=20)
            return

        # Show loading animation
        self.conv_progress.grid(row=3, column=0, sticky="ew", pady=(0, 10))
        self.conv_progress.start()
        self.update_idletasks()
        
        def process_conversion():
            time.sleep(0.2) # Brief UX feedback
            result = generate_drive_links(url)
            
            def render_results():
                self.conv_progress.stop()
                self.conv_progress.grid_forget()
                
                if result["status"] == "error":
                    err_card = ctk.CTkFrame(self.conv_results_frame, fg_color="#3A1D1D", corner_radius=10)
                    err_card.pack(fill="x", pady=10, padx=5)
                    ctk.CTkLabel(err_card, text=result["message"], font=ctk.CTkFont(weight="bold"), text_color="#FF6B6B").pack(pady=15, padx=20)
                    return
                    
                if result.get("notice"):
                    notice_card = ctk.CTkFrame(self.conv_results_frame, fg_color="#3A331D", corner_radius=10)
                    notice_card.pack(fill="x", pady=(0, 10), padx=5)
                    ctk.CTkLabel(notice_card, text=result["notice"], font=ctk.CTkFont(size=12, weight="bold"), text_color="#FFD166").pack(pady=12, padx=15)

                # Render link cards
                for item in result["links"]:
                    card = ctk.CTkFrame(self.conv_results_frame, corner_radius=12, fg_color=("#EFEFEF", "#24242A"))
                    card.pack(fill="x", pady=8, padx=5)
                    card.grid_columnconfigure(0, weight=1)
                    
                    # Card Header Title
                    title_lbl = ctk.CTkLabel(card, text=item["title"], font=ctk.CTkFont(size=14, weight="bold"))
                    title_lbl.grid(row=0, column=0, sticky="w", padx=15, pady=(12, 2))
                    
                    desc_lbl = ctk.CTkLabel(card, text=item["desc"], text_color="gray", font=ctk.CTkFont(size=11))
                    desc_lbl.grid(row=1, column=0, sticky="w", padx=15, pady=(0, 8))
                    
                    # URL Row with Copy and Open buttons
                    url_row = ctk.CTkFrame(card, fg_color="transparent")
                    url_row.grid(row=2, column=0, sticky="ew", padx=15, pady=(0, 12))
                    url_row.grid_columnconfigure(0, weight=1)
                    
                    url_entry = ctk.CTkEntry(url_row, height=36)
                    url_entry.grid(row=0, column=0, sticky="ew", padx=(0, 8))
                    url_entry.insert(0, item["url"])
                    
                    def copy_url(target_url=item["url"]):
                        self.clipboard_clear()
                        self.clipboard_append(target_url)
                        toast = ctk.CTkLabel(card, text="✓ הועתק ללוח!", text_color="#2FA572", font=ctk.CTkFont(size=11, weight="bold"))
                        toast.grid(row=0, column=1, sticky="e", padx=15)
                        self.after(2000, lambda: toast.destroy())
                        
                    btn_copy = ctk.CTkButton(url_row, text="העתק 📋", width=75, height=36,
                                             fg_color="#2FA572", hover_color="#107C41",
                                             command=copy_url)
                    btn_copy.grid(row=0, column=1, padx=(0, 5))
                    
                    btn_open = ctk.CTkButton(url_row, text="פתח 🌐", width=65, height=36,
                                             fg_color="#333344", hover_color="#555566",
                                             command=lambda u=item["url"]: webbrowser.open(u))
                    btn_open.grid(row=0, column=2)

            self.after(0, render_results)
            
        threading.Thread(target=process_conversion, daemon=True).start()

    # --- Store Manager App Functions ---

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
        self.show_store_view()
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
        self.show_store_view()
        file_path = filedialog.askopenfilename(
            title="בחר קובץ APK / XAPK",
            filetypes=[("Android Packages", "*.apk *.xapk"), ("All Files", "*.*")]
        )
        if not file_path:
            return
            
        apk_path = Path(file_path)
        self.show_status("⏳ קורא נתוני קובץ...", is_success=True)
        
        def process_apk():
            try:
                meta = extract_apk_info(apk_path)
                pkg = meta["package_name"]
                
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
                
                # Fetch Play Store Data
                play_data = None
                if play_scraper:
                    try:
                        play_data = play_scraper(pkg, lang="iw", country="il")
                    except Exception: pass
                
                # Handle Icon
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

                if play_data and play_data.get("description"):
                    app_data["description"] = clean_description(play_data["description"])
                if play_data and play_data.get("screenshots"):
                    app_data["screenshots"] = play_data["screenshots"][:5]
                
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
                    self.entry_drive.insert(0, "")
                    
                    self.combo_category.set("כללי")
                    
                    self.text_desc.delete("1.0", "end")
                    self.text_desc.insert("1.0", app_data["description"])
                    
                    self.btn_delete.configure(state="disabled")
                    self.btn_save_publish.configure(state="normal")
                    self.show_status("✅ הקובץ נטען בהצלחה! הדבק קישור לדרייב ולחץ 'שמור ופרסם'.", is_success=True)
                    
                self.after(0, update_ui)
                
            except Exception as e:
                self.after(0, lambda: self.show_status(f"❌ שגיאה בקריאת הקובץ: {e}", is_error=True))
                
        threading.Thread(target=process_apk, daemon=True).start()

    def delete_current_app(self):
        pkg = self.current_app_data.get("packageName")
        if not pkg:
            return
            
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
                self.current_app_data["name"] = name
                self.current_app_data["apkUrl"] = url
                self.current_app_data["category"] = self.combo_category.get()
                self.current_app_data["description"] = clean_description(self.text_desc.get("1.0", "end"))
                
                pkg = self.current_app_data["packageName"]
                
                found = False
                for i, app in enumerate(self.apps_data):
                    if app.get("packageName") == pkg:
                        self.apps_data[i] = self.current_app_data
                        found = True
                        break
                if not found:
                    self.apps_data.append(self.current_app_data)
                    
                save_apps_json(self.apps_data)
                
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
