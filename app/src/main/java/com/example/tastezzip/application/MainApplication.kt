package com.example.tastezzip.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@HiltAndroidApp
class MainApplication: Application() {
}

class LoadingManager {
    private val _loadingState = MutableSharedFlow<Boolean>(replay = 1)
    val loadingState: SharedFlow<Boolean> = _loadingState

    fun setLoadingState(isLoading: Boolean) {
        _loadingState.tryEmit(isLoading)
    }
}