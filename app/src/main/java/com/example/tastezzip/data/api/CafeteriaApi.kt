package com.example.tastezzip.data.api

import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.request.BookmarkCafeteriaRequestVo
import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.request.bookmark.DeleteBookmarkRequestVo
import com.example.tastezzip.model.request.comment.get.GetCommentRequestVo
import com.example.tastezzip.model.request.comment.post.CreateCommentRequestVo
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.model.response.cafeteria.bookmark.BookmarkListResponse
import com.example.tastezzip.model.response.cafeteria.detail.CafeteriaDetailResponse
import com.example.tastezzip.model.response.cafeteria.recommendation.RecommendResponseItem
import com.example.tastezzip.model.response.comment.get.GetCommentResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
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
        @Path("cafeteriaId") id: Long
    ): CafeteriaDetailResponse

    @POST(Endpoints.Cafeteria.BOOKMARK)
    suspend fun bookmarkCafeteria(@Body request: BookmarkCafeteriaRequestVo)

    @DELETE(Endpoints.Cafeteria.BOOKMARK)
    suspend fun deleteBookmark(
        @Query("cafeteriaId") cafeteriaId: Long,
        @Query("type") type: LikeType
    )

    @GET(Endpoints.Cafeteria.LIKE)
    suspend fun getBookmarkList(): BookmarkListResponse

    @GET(Endpoints.Cafeteria.COMMENT)
    suspend fun getComment(
        @Path("cafeteriaId") id: Long,
        @Query("pageable") pageable: GetCommentRequestVo
    ): GetCommentResponse

    @POST(Endpoints.Cafeteria.COMMENT)
    suspend fun createComment(
        @Path("cafeteriaId") id: Long,
        @Body request: CreateCommentRequestVo
    )

    @GET(Endpoints.Cafeteria.RECOMMENDATION)
    suspend fun getRecommendationList(): List<RecommendResponseItem>
}