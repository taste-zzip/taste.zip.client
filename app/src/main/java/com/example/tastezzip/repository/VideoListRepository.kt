package com.example.tastezzip.repository

import com.example.tastezzip.model.response.cafeteria.detail.Video

interface VideoListRepository {
    fun setVideoList(videoList: List<Video>)
    fun getVideoList(): List<Video>
    fun setCafeteriaId(id: Long)
    fun getCafeteriaId(): Long
}