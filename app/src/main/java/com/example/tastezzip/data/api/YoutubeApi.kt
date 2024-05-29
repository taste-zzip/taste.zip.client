package com.example.tastezzip.data.api

import com.example.tastezzip.model.request.SizeRequestVo
import com.example.tastezzip.model.response.youtube.YoutubeLikeCafeteriaResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface YoutubeApi {
    @POST(Endpoints.Youtube.LIKE)
    suspend fun getYoutubeLikeCafeteriaList(@Body request: SizeRequestVo): YoutubeLikeCafeteriaResponse
}