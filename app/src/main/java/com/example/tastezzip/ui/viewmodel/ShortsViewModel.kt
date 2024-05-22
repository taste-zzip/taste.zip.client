package com.example.tastezip.ui.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class VideoItem(
    val videoId: String,
    val title: String,
    val thumbnail: String,
    val id: Long
)

@HiltViewModel
@OptIn(ExperimentalFoundationApi::class)
class ShortsViewModel @Inject constructor() : ViewModel() {
    val videoList = listOf(
        VideoItem(
            "R1efJCVdaFU",
            "래퍼들은 가사를 어떻게 쓸까?",
            "https://thumb.mt.co.kr/06/2024/03/2024030110307220960_4.jpg",
            1
        ),
        VideoItem(
            "uGTE7wnGMbg",
            "래퍼들은 가사를 어떻게 쓸까?",
            "https://thumb.mt.co.kr/06/2024/03/2024030110307220960_4.jpg",
            2
        ),
        VideoItem(
            "ZpMvn9NivMU",
            "래퍼들은 가사를 어떻게 쓸까?",
            "https://thumb.mt.co.kr/06/2024/03/2024030110307220960_4.jpg",
            3
        ),
        VideoItem(
            "0g42vsNH-wA",
            "래퍼들은 가사를 어떻게 쓸까?",
            "https://thumb.mt.co.kr/06/2024/03/2024030110307220960_4.jpg",
            4
        ),
        VideoItem(
            "L3brB2rpXh0",
            "래퍼들은 가사를 어떻게 쓸까?",
            "https://thumb.mt.co.kr/06/2024/03/2024030110307220960_4.jpg",
            5
        )
    )
    private val _pageStateFlow: MutableStateFlow<PagerState> = MutableStateFlow(PagerState(currentPage = 0, currentPageOffsetFraction = 0.0f) { videoList.size })
    private val _currentPageIdx: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _currentVideoItem: MutableStateFlow<VideoItem> = MutableStateFlow(videoList[0])
    val pageStateFlow = _pageStateFlow.asStateFlow()
    val currentPageIdx = _currentPageIdx.asStateFlow()
    val currentVideoItem = _currentVideoItem.asStateFlow()

    fun updateCurrentPageIdx(index: Int) {
        _currentPageIdx.update { index }
    }

    fun updateCurrentItem(index: Int) {
        _currentVideoItem.update { videoList[index] }
    }
}

