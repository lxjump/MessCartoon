package com.mess.messcartoon.utils

import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

// UiEvent 类型
sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : UiEvent()
}

// 工具类对象
object UiEventUtils {
    private val _uiEventFlow = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun showToast(message: String) {
        _uiEventFlow.tryEmit(UiEvent.ShowToast(message))
    }

    fun showSnackbar(message: String, actionLabel: String? = null) {
        _uiEventFlow.tryEmit(UiEvent.ShowSnackbar(message, actionLabel))
    }
}

// 通用 UI 事件监听器 Composable
@Composable
fun HandleUiEvent(
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        UiEventUtils.uiEventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }
            }
        }
    }
}
