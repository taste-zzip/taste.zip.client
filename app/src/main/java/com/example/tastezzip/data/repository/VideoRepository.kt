package com.example.tastezzip.data.repository

import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.request.AddLikeVideoRequestVo
import com.example.tastezzip.model.request.DeleteLikeVideoRequestVo
import com.example.tastezzip.model.response.shorts.ShortsFeedResponse

interface VideoRepository {
    suspend fun addLikeVideo(request: AddLikeVideoRequestVo)
    suspend fun deleteLikeVideo(videoId: Long, type: LikeType = LikeType.LIKE)
    suspend fun getFeedList(request: Long): ShortsFeedResponse
}