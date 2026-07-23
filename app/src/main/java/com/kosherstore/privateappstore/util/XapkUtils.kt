package com.kosherstore.privateappstore.util

import java.io.File
import java.util.zip.ZipFile

object XapkUtils {
    fun isXapk(file: File): Boolean {
        return try {
            ZipFile(file).use { zip ->
                zip.getEntry("manifest.json") != null || zip.entries().asSequence().any { it.name.endsWith(".apk") && it.name != "base.apk" }
            }
        } catch (e: Exception) {
            false
        }
    }
}
