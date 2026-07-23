package com.kosherstore.privateappstore.ui.common

import android.content.Intent

sealed interface UiEvent {
    data class LaunchIntent(val intent: Intent) : UiEvent
    data class Message(val value: String) : UiEvent
}
