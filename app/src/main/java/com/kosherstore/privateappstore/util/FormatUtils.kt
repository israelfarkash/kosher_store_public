package com.kosherstore.privateappstore.util

import android.content.Context
import android.text.format.Formatter

object FormatUtils {

    fun formatBytes(context: Context, bytes: Long): String {
        return Formatter.formatShortFileSize(context, bytes)
    }
}
