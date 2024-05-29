package com.example.tastezzip.repository

import com.example.tastezzip.model.response.cafeteria.detail.Video

object VideoRepositoryImpl: VideoListRepository {
    private var videoList: List<Video> = emptyList()
    private var cafeteriaId: Long = -1

    override fun setVideoList(videoList: List<Video>) {
        this.videoList = videoList
    }

    override fun getVideoList(): List<Video> {
        return videoList
    }

    override fun setCafeteriaId(id: Long) {
        cafeteriaId = id
    }

    override fun getCafeteriaId(): Long {
        return cafeteriaId
    }
}