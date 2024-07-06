package com.example.tastezzip.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.application.MainApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel(
    private val loadingManager: LoadingManager
): ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    fun setLoading(loading: Boolean) {
        loadingManager.setLoadingState(loading)
    }

    private fun startLoading() {
        _isLoading.value = true
    }

    private fun endLoading() {
        _isLoading.value = false
    }

    protected fun <T> handleResult(
        result: ApiResult<T>,
        onSuccess: (T) -> Unit,
        onError: ((Int) -> Unit)? = null
    ) {
        when(result) {
            is ApiResult.Failure -> {
                // 에러 코드에 따른 예외 처리를 여기에서 하면 됨
                onError?.invoke(result.error)
                endLoading()
            }
            is ApiResult.Success -> {
                result.data?.let { onSuccess.invoke(it) }
                endLoading()
            }
            ApiResult.Loading -> startLoading()
        }
    }
}