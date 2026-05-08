package com.kosherstore.privateappstore.data.install

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class PackageChangeNotifier @Inject constructor() {
    private val _changes = MutableStateFlow(System.currentTimeMillis())
    val changes: StateFlow<Long> = _changes.asStateFlow()

    fun notifyChanged() {
        _changes.value = System.currentTimeMillis()
    }
}
