package com.example.tastezzip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.data.repository.VideoRepository
import com.example.tastezzip.model.response.shorts.Feed
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
    private val videoRepository: VideoRepository
) : ViewModel() {
    private val _videoList: MutableStateFlow<List<Feed>> = MutableStateFlow(emptyList())
    val videoList = _videoList.asStateFlow()

    init {
        getFeedList()
    }

    fun getFeedList() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = videoRepository.getFeedList(5)
            _videoList.update { response.feedList }
        }
    }
}