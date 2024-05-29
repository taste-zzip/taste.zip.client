package com.example.tastezzip.data.repositoryImpl

import android.util.Log
import com.example.tastezzip.data.api.YoutubeApi
import com.example.tastezzip.data.repository.YoutubeRepository
import com.example.tastezzip.model.request.SizeRequestVo
import com.example.tastezzip.model.response.youtube.YoutubeLikeCafeteriaResponse
import javax.inject.Inject
import kotlin.reflect.typeOf

class YoutubeRepositoryImpl @Inject constructor(
    private val youtubeApi: YoutubeApi
) : YoutubeRepository {
    override suspend fun getYoutubeLikeCafeteriaList(request: SizeRequestVo): YoutubeLikeCafeteriaResponse {
        return youtubeApi.getYoutubeLikeCafeteriaList(request)
    }
}