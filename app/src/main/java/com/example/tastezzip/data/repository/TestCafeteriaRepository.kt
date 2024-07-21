package com.example.tastezzip.data.repository

import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.ui.base.ApiResponse
import com.example.tastezzip.ui.base.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TestCafeteriaRepository {
    suspend fun searchCafeteria(keyword: String, pageable: SearchCafeteriaRequest): Flow<ApiResult<SearchCafeteriaResponse>>
}