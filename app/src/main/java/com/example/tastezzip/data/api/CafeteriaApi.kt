package com.example.tastezzip.data.api

import com.example.tastezzip.model.request.BookmarkCafeteriaRequestVo
import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.model.response.cafeteria.bookmark.BookmarkListResponse
import com.example.tastezzip.model.response.cafeteria.detail.CafeteriaDetailResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CafeteriaApi {
    @GET(Endpoints.Cafeteria.LIST)
    suspend fun searchCafeteria(
        @Query("keyword") keyword: String,
        @Query("pageable") pageable: SearchCafeteriaRequest
    ): SearchCafeteriaResponse

    @GET(Endpoints.Cafeteria.CAFETERIAID)
    suspend fun getCafeteriaDetail(
        @Path("id") id: Long
    ): CafeteriaDetailResponse

    @POST(Endpoints.Cafeteria.BOOKMARK)
    suspend fun bookmarkCafeteria(@Body request: BookmarkCafeteriaRequestVo)

    @GET(Endpoints.Cafeteria.LIKE)
    suspend fun getBookmarkList(): BookmarkListResponse
}