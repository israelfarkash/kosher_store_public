# Private App Store

פרויקט Android מלא ל"חנות אפליקציות פרטית" ב-Kotlin עם `MVVM`, `Room`, `Retrofit`, `DownloadManager`, `WorkManager`, `Hilt` ו-Material Design 3.

## לפני הרצה

✅ **הגדרות הושלמו!** הקוד עודכן להשתמש במאגר GitHub שלך:
- ריפו: `https://github.com/ASDFG0537701349/kosher-app-apks-public`
- קבצי JSON ואייקונים ייטענו מ-`https://raw.githubusercontent.com/ASDFG0537701349/kosher-app-apks-public/refs/heads/main/`

לפני הרצה, ודא:
1. קבצי ה-APK הועלו ל-Google Drive עם שיתוף `Anyone with the link` כ-Viewer
2. האייקונים נמצאים בתיקיית `icons/` בריפו (כבר קיימים: `com.bnhp.payments.paymentsapp.png`, `com.egged.egg.png`)
3. קבצי ה-JSON (`apps.json` ו-`app/src/main/assets/apps_seed.json`) מעודכנים עם הנתונים הנכונים

למידע על עדכון הקבצים, ראה פרק "סקריפט ניהול" למטה.

## מאיפה מגיעים השדות

האפליקציה קוראת את כל המידע מתוך `apps.json`. היא לא מנחשת אייקונים או משקל קבצים לבד.

`apkUrl`: קישור ישיר ל-APK בדרייב, בפורמט `https://drive.google.com/uc?export=download&id=APK_FILE_ID`.

`iconUrl`: קישור Raw לאייקון PNG/WebP ב-GitHub, למשל `https://raw.githubusercontent.com/USER/REPO/main/icons/package.name.png`.

`size`: טקסט שמגיע מה-JSON, למשל `28 MB`. אפשר להזין ידנית או לתת לסקריפט הניהול לחשב.

`checksum`: ערך SHA-256 או MD5 שמחושב אחרי שיש APK סופי. האפליקציה תבדוק אותו לפני התקנה.

בדיקת checksum ב-macOS:

```bash
shasum -a 256 app-release.apk
```

## סקריפט ניהול

הסקריפט [tools/store_manager.py](/Users/israelfarkash/Downloads/חנות%20אפליקציות%20אונליין%20כשרה%20-%20מבוססת%20על%20דרייב/tools/store_manager.py) מקבל קישור הורדה ישיר ל-APK, מוריד את הקובץ זמנית למחשב, מחלץ שם, package, גרסה, גודל, MD5 ואייקון, ואז מוסיף או מעדכן רשומה ב-`apps.json`.

הדבר היחיד שחייבים להדביק הוא קישור ה-APK. קטגוריה ותיאור לא קיימים באמת בתוך APK, לכן הסקריפט ממלא אותם כברירת מחדל ואפשר לערוך אותם לפני שמירה.

התקנת תלויות:

```bash
python3 -m pip install -r tools/requirements.txt
```

הרצה:

```bash
python3 tools/store_manager.py
```

## הערה חשובה

`DownloadManager` לא תומך Resume אמיתי לאחר השהיה. לכן כפתור "המשך" באפליקציה מפעיל הורדה מחדש בצורה בטוחה, תוך שמירה על חוויית משתמש עקבית.

אם APK גדול מאוד ו-Google Drive מציג מסך אישור/סריקת וירוסים במקום הורדה ישירה, `DownloadManager` עלול להוריד HTML במקום APK. במקרה כזה כדאי להשתמש באחסון שנותן direct download יציב יותר, או לבדוק בפועל את הקישור דרך האפליקציה.
