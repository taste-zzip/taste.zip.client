package com.example.tastezzip.ui.base

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.Response
import java.io.Reader

abstract class BaseRepository {
    inline fun <reified D> apiLaunch(
        crossinline apiCall: suspend () -> Response<ApiResponse<D>>
    ): Flow<ApiResult<D>> = flow {
        val response = apiCall.invoke()

        Log.e("BaseRepository response 결과", response.toString())

        when (response.isSuccessful) {
            true -> {
                val apiResponse = response.body()
                val data = apiResponse?.data
                emit(ApiResult.Success(data))
            }
            false -> {
                val apiResponse: ApiResponse<D> = fromGson(response.errorBody()?.charStream())
                val data = apiResponse.data
                val code = apiResponse.code

                emit(ApiResult.Failure(data, code))
            }
        }
    }.onStart { emit(ApiResult.Loading) }.catch { e ->
        e.printStackTrace()
        emit(ApiResult.Failure(null, 400))
    }

    inline fun <reified D> fromGson(json: Reader?): ApiResponse<D> {
        return Gson().fromJson(json, object: TypeToken<ApiResponse<D>>() {}.type) ?: ApiResponse()
    }
}

