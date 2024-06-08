package com.example.tastezzip.data.repositoryImpl

import com.example.tastezzip.data.api.CafeteriaApi
import com.example.tastezzip.data.repository.CafeteriaRepository
import com.example.tastezzip.model.request.BookmarkCafeteriaRequestVo
import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.request.comment.get.GetCommentRequestVo
import com.example.tastezzip.model.request.comment.post.CreateCommentRequestVo
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.model.response.cafeteria.bookmark.BookmarkListResponse
import com.example.tastezzip.model.response.cafeteria.detail.CafeteriaDetailResponse
import com.example.tastezzip.model.response.comment.get.GetCommentResponse
import javax.inject.Inject

class CafeteriaRepositoryImpl @Inject constructor(
    private val cafeteriaApi: CafeteriaApi
) : CafeteriaRepository {
    override suspend fun searchCafeteria(
        keyword: String,
        pageable: SearchCafeteriaRequest
    ): SearchCafeteriaResponse {
        return cafeteriaApi.searchCafeteria(keyword, pageable)
    }

    override suspend fun getCafeteriaDetail(id: Long): CafeteriaDetailResponse {
        return cafeteriaApi.getCafeteriaDetail(id)
    }

    override suspend fun bookmarkCafeteria(request: BookmarkCafeteriaRequestVo) {
        return cafeteriaApi.bookmarkCafeteria(request)
    }

    override suspend fun getBookmarkList(): BookmarkListResponse {
        return cafeteriaApi.getBookmarkList()
    }

    override suspend fun getComment(id: Long, request: GetCommentRequestVo): GetCommentResponse {
        return cafeteriaApi.getComment(id = id, pageable = request)
    }

    override suspend fun createComment(id: Long, request: CreateCommentRequestVo) {
        return cafeteriaApi.createComment(id = id, request = request)
    }
}