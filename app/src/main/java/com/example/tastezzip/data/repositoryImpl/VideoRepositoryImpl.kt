package com.example.tastezzip.data.repositoryImpl

import com.example.tastezzip.data.api.VideoApi
import com.example.tastezzip.data.repository.VideoRepository
import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.request.AddLikeVideoRequestVo
import com.example.tastezzip.model.request.DeleteLikeVideoRequestVo
import com.example.tastezzip.model.response.shorts.ShortsFeedResponse
import com.example.tastezzip.model.response.worldcup.WorldCupListResponse
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi
) : VideoRepository {
    override suspend fun addLikeVideo(request: AddLikeVideoRequestVo) {
        return videoApi.addLikeVideo(request)
    }

    override suspend fun deleteLikeVideo(videoId: Long, type: LikeType) {
        return videoApi.deleteLikeVideo(videoId)
    }

    override suspend fun getFeedList(request: Long): ShortsFeedResponse {
        return videoApi.getFeedList(request)
    }

    override suspend fun getWorldCupList(): WorldCupListResponse {
        return videoApi.getWorldCupList()
    }
}