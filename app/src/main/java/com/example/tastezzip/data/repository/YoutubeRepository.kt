package com.example.tastezzip.data.repository

import com.example.tastezzip.model.request.SizeRequestVo
import com.example.tastezzip.model.response.youtube.YoutubeLikeCafeteriaResponse

interface YoutubeRepository {
    suspend fun getYoutubeLikeCafeteriaList(request: SizeRequestVo): YoutubeLikeCafeteriaResponse
}