package com.example.tastezzip.ui.viewmodel

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.data.repository.VideoRepository
import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.request.AddLikeVideoRequestVo
import com.example.tastezzip.model.response.shorts.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalFoundationApi::class)
class ShortsViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {
    private val _pageStateFlow: MutableStateFlow<PagerState> = MutableStateFlow(PagerState(currentPage = 0, currentPageOffsetFraction = 0.0f) { 0 })
    private val _likeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val pageStateFlow = _pageStateFlow.asStateFlow()
    val likeState: StateFlow<Boolean> = _likeStateFlow.asStateFlow()

    fun updatePagerState(videoSize: Int, index: Int) {
        viewModelScope.launch {
            _pageStateFlow.update { currentState ->
                PagerState(currentPage = index, currentPageOffsetFraction = currentState.currentPageOffsetFraction) { videoSize }
            }
        }
    }

    fun updateLikeState(current: LikeType?, videoId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            if (current == LikeType.LIKE) {
                videoRepository.deleteLikeVideo(videoId)
                _likeStateFlow.update { false }
            } else {
                videoRepository.addLikeVideo(AddLikeVideoRequestVo(videoId = videoId, type = LikeType.LIKE, score = null))
                _likeStateFlow.update { true }
            }

        }
    }

    fun createVideoRating(videoId: Long, review: Double) {
        viewModelScope.launch {
            videoRepository.addLikeVideo(request = AddLikeVideoRequestVo(videoId = videoId, type = LikeType.STAR, score = review))
        }
    }
}

