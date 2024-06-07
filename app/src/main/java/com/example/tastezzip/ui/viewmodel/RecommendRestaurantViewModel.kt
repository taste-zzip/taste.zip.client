package com.example.tastezzip.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tastezzip.model.vo.RecommendItemVo
import com.example.tastezzip.model.vo.VideoItemVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecommendRestaurantViewModel @Inject constructor(): ViewModel() {
    private val _recommendListStateFlow: MutableStateFlow<List<RecommendItemVo>> = MutableStateFlow(
        listOf(
            RecommendItemVo("더진국 중앙대점", "국밥전문점", 239, 2319, listOf(VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"), VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"), VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"), VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"), VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"), VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"), VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"))),
            RecommendItemVo("더진국 중앙대점", "국밥전문점", 239, 2319, listOf(VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"))),
            RecommendItemVo("더진국 중앙대점", "국밥전문점", 239, 2319, listOf(VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"))),
            RecommendItemVo("더진국 중앙대점", "국밥전문점", 239, 2319, listOf(VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"))),
            RecommendItemVo("더진국 중앙대점", "국밥전문점", 239, 2319, listOf(VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999"))),
            RecommendItemVo("더진국 중앙대점", "국밥전문점", 239, 2319, listOf(VideoItemVo(videoPk = "3MKc6RXIhRw", thumbnailUrl = "https://oasisproduct.cdn.ntruss.com/78096/thumb/999")))
        )
    )
    val recommendList = _recommendListStateFlow.asStateFlow()
}