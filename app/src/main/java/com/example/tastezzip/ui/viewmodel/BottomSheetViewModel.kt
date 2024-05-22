package com.example.tastezip.ui.viewmodel

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezip.model.vo.VideoItemVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(

): ViewModel() {
    private val _shopTitleState: MutableStateFlow<String> = MutableStateFlow("")
    private val _distanceState: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _typeState: MutableStateFlow<String> = MutableStateFlow("")
    private val _videoCountState: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _videoListState: MutableStateFlow<List<VideoItemVo>> = MutableStateFlow(emptyList())
    val shopTitleState: StateFlow<String> = _shopTitleState
    val distanceState: StateFlow<Int> = _distanceState
    val typeState: StateFlow<String> = _typeState
    val videoCountState: StateFlow<Int> = _videoCountState
    val videoListState: StateFlow<List<VideoItemVo>> = _videoListState

    init {
        initView()
    }

    private fun initView() {
        viewModelScope.launch(Dispatchers.Main) {
            _shopTitleState.update {
                "바다회사랑"
            }
            _distanceState.update {
                225
            }
            _typeState.update {
                "횟집"
            }
            _videoCountState.update {
                25
            }
            _videoListState.update {
                listOf(
                    VideoItemVo(
                        id = 1,
                        platform = "",
                        viewCount = 10,
                        thumbnailUrl = "https://www.esquirekorea.co.kr/resources_old/online/org_online_image/eq/71a09951-6165-4bef-842d-955aa99e90e6.jpg",
                        title = "뭉티기 맛집!",
                    ),
                    VideoItemVo(
                        id = 1,
                        platform = "",
                        viewCount = 10,
                        thumbnailUrl = "https://www.esquirekorea.co.kr/resources_old/online/org_online_image/eq/71a09951-6165-4bef-842d-955aa99e90e6.jpg",
                        title = "뭉티기 맛집!",
                    ),
                    VideoItemVo(
                        id = 1,
                        platform = "",
                        viewCount = 10,
                        thumbnailUrl = "https://www.esquirekorea.co.kr/resources_old/online/org_online_image/eq/71a09951-6165-4bef-842d-955aa99e90e6.jpg",
                        title = "뭉티기 맛집!",
                    ),
                    VideoItemVo(
                        id = 1,
                        platform = "",
                        viewCount = 10,
                        thumbnailUrl = "https://www.esquirekorea.co.kr/resources_old/online/org_online_image/eq/71a09951-6165-4bef-842d-955aa99e90e6.jpg",
                        title = "뭉티기 맛집!",
                    ),
                    VideoItemVo(
                        id = 1,
                        platform = "",
                        viewCount = 10,
                        thumbnailUrl = "https://www.esquirekorea.co.kr/resources_old/online/org_online_image/eq/71a09951-6165-4bef-842d-955aa99e90e6.jpg",
                        title = "뭉티기 맛집!",
                    )
                )
            }
        }
    }
}