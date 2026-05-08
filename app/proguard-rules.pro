# שמירת מודלי הנתונים של JSON/Room כדי למנוע שבירת מיפוי בזמן אופטימיזציה.
-keep class com.kosherstore.privateappstore.data.remote.dto.** { *; }
-keep class com.kosherstore.privateappstore.data.local.** { *; }
-keep class com.kosherstore.privateappstore.domain.model.** { *; }

-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keepattributes Signature,*Annotation*
