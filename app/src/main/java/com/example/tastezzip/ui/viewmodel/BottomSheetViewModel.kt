package com.example.tastezzip.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.data.repository.CafeteriaRepository
import com.example.tastezzip.model.request.BookmarkCafeteriaRequestVo
import com.example.tastezzip.model.request.comment.get.GetCommentRequestVo
import com.example.tastezzip.model.request.comment.post.CreateCommentRequestVo
import com.example.tastezzip.model.response.cafeteria.detail.CafeteriaDetailResponse
import com.example.tastezzip.model.response.cafeteria.detail.Video
import com.example.tastezzip.model.response.comment.get.Content
import com.example.tastezzip.model.vo.VideoItemVo
import com.example.tastezzip.repository.VideoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val cafeteriaRepository: CafeteriaRepository
): ViewModel() {
    private val _cafeteriaDetail: MutableStateFlow<CafeteriaDetailResponse> = MutableStateFlow(CafeteriaDetailResponse())
    private val _bookmarkSuccessEvent = MutableSharedFlow<String>()
    private val _isLoading = MutableSharedFlow<Boolean>()
    private val _commentList: MutableStateFlow<List<Content>> = MutableStateFlow(emptyList())
    private val _goToCafeteriaCommentEvent: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val cafeteriaDetail = _cafeteriaDetail.asStateFlow()
    val bookmarkSuccessEvent = _bookmarkSuccessEvent.asSharedFlow()
    val isLoading = _isLoading.asSharedFlow()
    val commentList = _commentList.asStateFlow()
    val goToCafeteriaCommentEvent = _goToCafeteriaCommentEvent.asSharedFlow()

    fun getCafeteriaDetail(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.emit(true)
            val response = cafeteriaRepository.getCafeteriaDetail(id)
            _cafeteriaDetail.update { response }
            _isLoading.emit(false)
        }
    }

    fun setVideoList(videoList: List<Video>) {
        viewModelScope.launch(Dispatchers.Main) {
            VideoRepositoryImpl.setVideoList(videoList)
        }
    }

    fun setCafeteriaId(id: Long) {
        viewModelScope.launch {
            VideoRepositoryImpl.setCafeteriaId(id)
        }
    }

    fun bookmarkCafeteria(id: Long) {
        viewModelScope.launch {
            try {
                cafeteriaRepository.bookmarkCafeteria(BookmarkCafeteriaRequestVo(id))
                _bookmarkSuccessEvent.emit("북마크에 등록하였습니다")
            } catch (e: Exception) {
                Log.e("bookmarkCafeteria", e.toString())
            }
        }
    }

    fun onClickBtnComment() {
        viewModelScope.launch {
            _goToCafeteriaCommentEvent.emit(true)
        }
    }

    fun getCafeteriaComment(id: Long) {
        viewModelScope.launch {
            try {
                val response = cafeteriaRepository.getComment(id = id, request = GetCommentRequestVo(page = 5, size = 5, sort = listOf("id,DESC")))
                Log.e("BottomSheetViewModel, getCafeteriaComment", response.toString())
                _commentList.update { response.commentList.content }
            } catch (e: Exception) {
                Log.e("BottomSheetViewModel, getCafeteriaComment()", e.toString())
            }
        }
    }

    fun createComment(id: Long, content: String) {
        viewModelScope.launch {
            try {
                Log.e("댓글 생성", content)
                cafeteriaRepository.createComment(id = id, request = CreateCommentRequestVo(content = content))
                getCafeteriaComment(id)
            } catch (e: Exception) {
                Log.e("createComment", e.toString())
            }
        }
    }
}