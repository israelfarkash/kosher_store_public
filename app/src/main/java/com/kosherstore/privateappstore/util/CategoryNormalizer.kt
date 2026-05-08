package com.kosherstore.privateappstore.util

object CategoryNormalizer {
    const val DEFAULT_CATEGORY = "כללי"

    fun normalize(value: String?): String {
        return value
            ?.trim()
            ?.replace(Regex("\\s+"), " ")
            ?.takeIf { it.isNotBlank() }
            ?: DEFAULT_CATEGORY
    }
}
