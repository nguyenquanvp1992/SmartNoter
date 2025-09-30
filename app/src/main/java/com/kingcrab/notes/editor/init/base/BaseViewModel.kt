package com.kingcrab.notes.editor.init.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected fun <T> emit(flow: Flow<T>, value: T) {
        viewModelScope.launch {
            if (flow is MutableStateFlow || flow is MutableSharedFlow) {
                flow.emit(value)
            }
        }
    }
}