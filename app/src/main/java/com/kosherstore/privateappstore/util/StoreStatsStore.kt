package com.kosherstore.privateappstore.util

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class StoreStatsStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _freedBytes = MutableStateFlow(prefs.getLong(KEY_FREED_BYTES, 0L))
    val freedBytes: StateFlow<Long> = _freedBytes.asStateFlow()

    fun addFreedBytes(bytes: Long) {
        if (bytes <= 0L) return
        val updated = _freedBytes.value + bytes
        prefs.edit { putLong(KEY_FREED_BYTES, updated) }
        _freedBytes.value = updated
    }

    companion object {
        private const val PREFS_NAME = "private_store_stats"
        private const val KEY_FREED_BYTES = "freed_bytes"
    }
}
