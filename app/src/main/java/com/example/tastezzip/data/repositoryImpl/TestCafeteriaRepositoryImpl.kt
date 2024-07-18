package com.example.tastezzip.data.repositoryImpl

import com.example.tastezzip.data.api.CafeteriaApi
import com.example.tastezzip.data.api.TestCafeteriaApi
import com.example.tastezzip.data.repository.TestCafeteriaRepository
import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.ui.base.ApiResponse
import com.example.tastezzip.ui.base.ApiResult
import com.example.tastezzip.ui.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class TestCafeteriaRepositoryImpl@Inject constructor(
    private val cafeteriaApi: TestCafeteriaApi
) : TestCafeteriaRepository, BaseRepository() {
    override suspend fun searchCafeteria(
        keyword: String,
        pageable: SearchCafeteriaRequest
    ): Flow<ApiResult<SearchCafeteriaResponse>> {
        return apiLaunch { cafeteriaApi.searchCafeteria(keyword, pageable) }
    }
}