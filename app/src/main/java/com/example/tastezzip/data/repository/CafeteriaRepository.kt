package com.example.tastezzip.data.repository

import com.example.tastezzip.model.request.BookmarkCafeteriaRequestVo
import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.model.response.cafeteria.bookmark.BookmarkListResponse
import com.example.tastezzip.model.response.cafeteria.detail.CafeteriaDetailResponse

interface CafeteriaRepository {
    suspend fun searchCafeteria(keyword: String, pageable: SearchCafeteriaRequest): SearchCafeteriaResponse
    suspend fun getCafeteriaDetail(id: Long): CafeteriaDetailResponse
    suspend fun bookmarkCafeteria(request: BookmarkCafeteriaRequestVo)
    suspend fun getBookmarkList(): BookmarkListResponse
}