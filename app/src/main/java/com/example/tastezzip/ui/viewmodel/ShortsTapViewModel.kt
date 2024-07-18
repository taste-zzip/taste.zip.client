package com.example.tastezzip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.data.repository.VideoRepository
import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.request.AddLikeVideoRequestVo
import com.example.tastezzip.model.response.shorts.Feed
import com.example.tastezzip.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortsTapViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    loadingManager: LoadingManager
) : BaseViewModel(loadingManager) {
    private val _videoList: MutableStateFlow<List<Feed>> = MutableStateFlow(emptyList())
    val videoList = _videoList.asStateFlow()

    init {
        getFeedList()
    }

    private fun getFeedList() {
        viewModelScope.launch(Dispatchers.Main) {
            setLoading(true)
            try {
                val response = videoRepository.getFeedList(5)
                _videoList.update { response.feedList }
            } catch (e: Exception) {

            } finally {
                setLoading(false)
            }
        }
    }

    fun createVideoRating(videoId: Long, review: Double) {
        viewModelScope.launch {
            setLoading(true)
            try {
                videoRepository.addLikeVideo(request = AddLikeVideoRequestVo(videoId = videoId, type = LikeType.STAR, score = review))
            } catch (e: Exception) {

            } finally {
                setLoading(false)
            }
        }
    }
}