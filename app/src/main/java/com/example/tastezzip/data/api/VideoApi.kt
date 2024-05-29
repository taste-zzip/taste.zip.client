package com.example.tastezzip.data.api

import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.request.AddLikeVideoRequestVo
import com.example.tastezzip.model.request.DeleteLikeVideoRequestVo
import com.example.tastezzip.model.response.shorts.ShortsFeedResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VideoApi {
    @POST(Endpoints.Video.ACCOUNT)
    suspend fun addLikeVideo(@Body request: AddLikeVideoRequestVo)

    @DELETE(Endpoints.Video.ACCOUNT)
    suspend fun deleteLikeVideo(videoId: Long, type: LikeType = LikeType.LIKE)

    @GET(Endpoints.Video.FEED)
    suspend fun getFeedList(@Query("size") size: Long): ShortsFeedResponse
}