package com.example.tastezzip.data.api

import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.ui.base.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TestCafeteriaApi {
    @GET(Endpoints.Cafeteria.LIST)
    suspend fun searchCafeteria(
        @Query("keyword") keyword: String,
        @Query("pageable") pageable: SearchCafeteriaRequest
    ): Response<ApiResponse<SearchCafeteriaResponse>>
}