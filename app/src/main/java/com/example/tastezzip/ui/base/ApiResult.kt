package com.example.tastezzip.ui.base

sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Failure<T>(val data: T?, val error: Int) : ApiResult<T>()
}