#!/usr/bin/env python3
"""
כלי מקומי לניהול apps.json של חנות אפליקציות פרטית.

דרישות:
    pip install pyaxmlparser pillow

הסקריפט מניח שהוא נמצא בתיקיית tools/ בתוך הריפו, וש-apps.json נמצא בשורש הריפו.
"""

from __future__ import annotations

import hashlib
import json
import shutil
import tkinter as tk
import urllib.parse
import zipfile
from dataclasses import dataclass
from pathlib import Path
from tkinter import filedialog, messagebox, ttk

from pyaxmlparser import APK

try:
    from PIL import Image
except ImportError:  # Pillow עוזר בהמרת WebP/אייקונים, אבל PNG רגיל יעבוד גם בלעדיו.
    Image = None


ROOT_DIR = Path(__file__).resolve().parents[1]
APPS_JSON_PATH = ROOT_DIR / "apps.json"
ICONS_DIR = ROOT_DIR / "icons"
DEFAULT_GITHUB_BASE = ""
CATEGORIES = ["לימוד", "חדשות", "פרודוקטיביות", "כלים", "תקשורת", "כללי"]


@dataclass
class ApkMetadata:
    apk_path: Path
    name: str
    package_name: str
    version_code: int
    version_name: str
    size: str
    checksum: str
    icon_path_in_apk: str | None


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
    if "drive.google.com" not in value:
        return value

    parsed = urllib.parse.urlparse(value)
    file_id = None
    if "/file/d/" in parsed.path:
        file_id = parsed.path.split("/file/d/", 1)[1].split("/", 1)[0]
    else:
        file_id = urllib.parse.parse_qs(parsed.query).get("id", [None])[0]

    if not file_id:
        return value

    return f"https://drive.google.com/uc?export=download&id={file_id}"


def load_apk_metadata(apk_path: Path) -> ApkMetadata:
    apk = APK(str(apk_path))
    package_name = read_apk_value(apk, "package", "get_package")
    if not package_name:
        raise RuntimeError("לא הצלחתי לחלץ Package Name מה-APK.")

    version_code = int(read_apk_value(apk, "version_code", "get_androidversion_code") or 0)
    version_name = read_apk_value(apk, "version_name", "get_androidversion_name") or str(version_code)
    app_name = read_apk_value(apk, "application", "get_app_name") or package_name

    icon_path = None
    try:
        icon_path = apk.get_app_icon()
    except Exception:
        icon_path = None

    return ApkMetadata(
        apk_path=apk_path,
        name=app_name,
        package_name=package_name,
        version_code=version_code,
        version_name=version_name,
        size=file_size_mb(apk_path),
        checksum=md5_checksum(apk_path),
        icon_path_in_apk=icon_path,
    )


def read_apk_value(apk: APK, *names: str) -> str | None:
    for name in names:
        value = getattr(apk, name, None)
        if callable(value):
            value = value()
        if value not in (None, ""):
            return str(value)
    return None


def extract_icon(metadata: ApkMetadata) -> Path:
    ICONS_DIR.mkdir(exist_ok=True)
    output_path = ICONS_DIR / f"{metadata.package_name}.png"

    with zipfile.ZipFile(metadata.apk_path) as archive:
        icon_path = choose_icon_path(archive, metadata.icon_path_in_apk)
        if not icon_path:
            raise RuntimeError("לא נמצא אייקון PNG/WebP בתוך ה-APK.")

        with archive.open(icon_path) as icon_file:
            temp_path = ICONS_DIR / f"{metadata.package_name}{Path(icon_path).suffix}"
            with temp_path.open("wb") as out:
                shutil.copyfileobj(icon_file, out)

    if temp_path.suffix.lower() == ".png":
        temp_path.replace(output_path)
        return output_path

    if Image is None:
        temp_path.replace(output_path)
        return output_path

    with Image.open(temp_path) as image:
        image.convert("RGBA").save(output_path, "PNG")
    temp_path.unlink(missing_ok=True)
    return output_path


def choose_icon_path(archive: zipfile.ZipFile, preferred: str | None) -> str | None:
    names = archive.namelist()
    supported = (".png", ".webp", ".jpg", ".jpeg")

    if preferred and preferred.lower().endswith(supported) and preferred in names:
        return preferred

    candidates = [
        name for name in names
        if name.lower().endswith(supported)
        and name.startswith("res/")
        and any(token in name.lower() for token in ("icon", "launcher", "ic_"))
    ]
    if not candidates:
        return None

    # בדרך כלל האייקון האיכותי ביותר יהיה הקובץ הגדול ביותר מבין המועמדים.
    return max(candidates, key=lambda name: archive.getinfo(name).file_size)


def load_apps_json() -> list[dict]:
    if not APPS_JSON_PATH.exists():
        return []

    with APPS_JSON_PATH.open("r", encoding="utf-8") as file:
        data = json.load(file)

    if not isinstance(data, list):
        raise ValueError("apps.json חייב להיות מערך JSON.")

    return data


def save_apps_json(apps: list[dict]) -> None:
    with APPS_JSON_PATH.open("w", encoding="utf-8") as file:
        json.dump(apps, file, ensure_ascii=False, indent=2)
        file.write("\n")


def upsert_app(app_entry: dict) -> None:
    apps = load_apps_json()
    package_name = app_entry["packageName"]
    updated = False

    for index, existing in enumerate(apps):
        if existing.get("packageName") == package_name:
            apps[index] = app_entry
            updated = True
            break

    if not updated:
        apps.append(app_entry)

    apps.sort(key=lambda item: item.get("name", "").lower())
    save_apps_json(apps)


class StoreManagerApp(tk.Tk):
    def __init__(self) -> None:
        super().__init__()
        self.title("Private Store Manager")
        self.geometry("780x620")
        self.minsize(700, 580)

        self.metadata: ApkMetadata | None = None

        self.github_base_var = tk.StringVar(value=DEFAULT_GITHUB_BASE)
        self.apk_url_var = tk.StringVar()

        # תיקון העתק-הדבק במק
        self.bind("<Command-c>", lambda e: self.event_generate("<<Copy>>"))
        self.bind("<Command-v>", lambda e: self.event_generate("<<Paste>>"))
        self.bind("<Command-x>", lambda e: self.event_generate("<<Cut>>"))
        self.bind("<Command-a>", lambda e: self.event_generate("<<SelectAll>>"))
        self.category_var = tk.StringVar(value=CATEGORIES[0])
        self.description_var = tk.StringVar()
        self.status_var = tk.StringVar(value="בחר APK מהמחשב כדי להתחיל.")

        self.name_var = tk.StringVar()
        self.package_var = tk.StringVar()
        self.version_code_var = tk.StringVar()
        self.version_name_var = tk.StringVar()
        self.size_var = tk.StringVar()
        self.checksum_var = tk.StringVar()

        self._build_ui()

    def _make_entry_with_paste(self, parent, variable, row, col=1) -> ttk.Entry:
        """יוצר שדה קלט עם כפתור הדבקה ליד."""
        frame = ttk.Frame(parent)
        frame.grid(row=row, column=col, sticky="ew", pady=6)
        frame.columnconfigure(0, weight=1)

        entry = ttk.Entry(frame, textvariable=variable)
        entry.grid(row=0, column=0, sticky="ew")

        paste_btn = ttk.Button(
            frame, text="📋 הדבק", width=8,
            command=lambda var=variable: self._paste_to_var(var),
        )
        paste_btn.grid(row=0, column=1, padx=(4, 0))

        return entry

    def _paste_to_var(self, var: tk.StringVar) -> None:
        """הדבקת תוכן מה-clipboard לתוך משתנה."""
        try:
            clipboard = self.clipboard_get()
            var.set(clipboard.strip())
        except tk.TclError:
            pass

    def _paste_to_text(self, text_widget: tk.Text) -> None:
        """הדבקת תוכן מה-clipboard לתוך Text widget."""
        try:
            clipboard = self.clipboard_get()
            text_widget.delete("1.0", tk.END)
            text_widget.insert("1.0", clipboard.strip())
        except tk.TclError:
            pass

    def _build_ui(self) -> None:
        container = ttk.Frame(self, padding=18)
        container.pack(fill=tk.BOTH, expand=True)
        container.columnconfigure(1, weight=1)

        # --- כפתור בחירת APK ---
        ttk.Label(container, text="קובץ APK").grid(row=0, column=0, sticky="w", pady=6)
        actions = ttk.Frame(container)
        actions.grid(row=0, column=1, sticky="w", pady=6)
        ttk.Button(actions, text="📂 בחר APK מהמחשב", command=self.choose_apk).pack(side=tk.LEFT)

        # --- סטטוס ---
        ttk.Label(container, textvariable=self.status_var).grid(row=1, column=1, sticky="w", pady=6)

        # --- שדה קישור הורדה ישיר (רק להטמעה ב-JSON) ---
        ttk.Label(container, text="קישור הורדה ישיר ל-APK").grid(row=2, column=0, sticky="w", pady=6)
        self._make_entry_with_paste(container, self.apk_url_var, row=2)

        # --- שדה קישור GitHub ---
        ttk.Label(container, text="קישור לפרויקט בגיטהאב").grid(row=3, column=0, sticky="w", pady=6)
        self._make_entry_with_paste(container, self.github_base_var, row=3)

        # --- שדות מידע (נטענים אוטומטית מה-APK, ניתנים לעריכה) ---
        info_fields = [
            ("שם אפליקציה", self.name_var, 4),
            ("Package Name", self.package_var, 5),
            ("Version Code", self.version_code_var, 6),
            ("Version Name", self.version_name_var, 7),
            ("גודל", self.size_var, 8),
            ("MD5", self.checksum_var, 9),
        ]

        for label, variable, row in info_fields:
            ttk.Label(container, text=label).grid(row=row, column=0, sticky="w", pady=6)
            self._make_entry_with_paste(container, variable, row=row)

        # --- קטגוריה ---
        ttk.Label(container, text="קטגוריה").grid(row=10, column=0, sticky="w", pady=6)
        ttk.Combobox(
            container,
            textvariable=self.category_var,
            values=CATEGORIES,
            state="readonly",
        ).grid(row=10, column=1, sticky="ew", pady=6)

        # --- תיאור עם כפתור הדבקה ---
        ttk.Label(container, text="תיאור").grid(row=11, column=0, sticky="nw", pady=6)
        desc_frame = ttk.Frame(container)
        desc_frame.grid(row=11, column=1, sticky="nsew", pady=6)
        desc_frame.columnconfigure(0, weight=1)
        desc_frame.rowconfigure(0, weight=1)

        self.description_text = tk.Text(desc_frame, height=5, wrap=tk.WORD)
        self.description_text.grid(row=0, column=0, sticky="nsew")

        paste_desc_btn = ttk.Button(
            desc_frame, text="📋 הדבק", width=8,
            command=lambda: self._paste_to_text(self.description_text),
        )
        paste_desc_btn.grid(row=0, column=1, padx=(4, 0), sticky="n")

        container.rowconfigure(11, weight=1)

        # --- כפתור עדכון ---
        ttk.Button(container, text="💾 Update JSON", command=self.update_json).grid(
            row=12,
            column=1,
            sticky="e",
            pady=18,
        )

    def choose_apk(self) -> None:
        selected = filedialog.askopenfilename(
            title="בחר APK",
            filetypes=[("Android APK", "*.apk"), ("All files", "*.*")],
        )
        if not selected:
            return

        try:
            self.status_var.set("מנתח את ה-APK...")
            self.update_idletasks()
            self.metadata = load_apk_metadata(Path(selected))
            self._fill_metadata_fields(self.metadata)
            self.status_var.set(f"✅ ה-APK נטען בהצלחה: {self.metadata.name}")
        except Exception as exc:
            self.status_var.set("❌ הניתוח נכשל.")
            messagebox.showerror("שגיאה", f"לא הצלחתי לקרוא את ה-APK:\n{exc}")

    def update_json(self) -> None:
        if self.metadata is None:
            messagebox.showwarning("חסר APK", "לחץ 'בחר APK מהמחשב' לפני עדכון ה-JSON.")
            return

        apk_url = normalize_drive_url(self.apk_url_var.get())
        if not apk_url:
            messagebox.showwarning("חסר קישור", "הדבק קישור הורדה ישיר ל-APK (מדרייב או ממקום אחר).")
            return

        try:
            extract_icon(self.metadata)
            package_name = self.package_var.get().strip()

            github_base = self.github_base_var.get().rstrip("/")
            if github_base.startswith("https://github.com/"):
                parts = [p for p in github_base.replace("https://github.com/", "").split("/") if p]
                if len(parts) >= 2:
                    user_org = parts[0]
                    repo = parts[1]
                    github_base = f"https://raw.githubusercontent.com/{user_org}/{repo}/main"
            if not github_base:
                messagebox.showwarning("חסר קישור", "אנא הזן את קישור הפרויקט בגיטהאב.")
                return

            description = self.description_text.get("1.0", tk.END).strip()

            app_entry = {
                "name": self.name_var.get().strip(),
                "packageName": package_name,
                "versionCode": int(self.version_code_var.get().strip()),
                "versionName": self.version_name_var.get().strip(),
                "apkUrl": apk_url,
                "iconUrl": f"{github_base}/icons/{package_name}.png",
                "description": description,
                "category": self.category_var.get().strip() or "כללי",
                "size": self.size_var.get().strip(),
                "checksum": self.checksum_var.get().strip(),
                "checksumType": "MD5",
            }

            upsert_app(app_entry)
            self.status_var.set("✅ apps.json עודכן. עכשיו נשאר git add/commit/push.")
            messagebox.showinfo("נשמר", "האפליקציה נוספה/עודכנה ב-apps.json והאייקון נשמר בתיקיית icons.")
        except Exception as exc:
            messagebox.showerror("שגיאה", f"לא הצלחתי לעדכן את ה-JSON:\n{exc}")

    def _fill_metadata_fields(self, metadata: ApkMetadata) -> None:
        self.name_var.set(metadata.name)
        self.package_var.set(metadata.package_name)
        self.version_code_var.set(str(metadata.version_code))
        self.version_name_var.set(metadata.version_name)
        self.size_var.set(metadata.size)
        self.checksum_var.set(metadata.checksum)
        self.category_var.set("כללי")
        self.description_text.delete("1.0", tk.END)
        self.description_text.insert("1.0", metadata.name)


if __name__ == "__main__":
    StoreManagerApp().mainloop()
