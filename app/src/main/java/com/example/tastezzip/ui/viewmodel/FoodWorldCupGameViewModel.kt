package com.example.tastezzip.ui.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.data.repository.VideoRepository
import com.example.tastezzip.model.response.worldcup.WorldCupListResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalFoundationApi::class)
class FoodWorldCupGameViewModel @Inject constructor(
    private val videoRepository: VideoRepository
): ViewModel() {
    private val _pagerState: MutableStateFlow<PagerState> = MutableStateFlow(PagerState(currentPage = 0, currentPageOffsetFraction = 0.0f) { 2 })
    private val _videoList: MutableStateFlow<List<WorldCupListResponseItem>> = MutableStateFlow(emptyList())
    private val _currentMatch: MutableStateFlow<Pair<Int, Int>> = MutableStateFlow(0 to 1)
    private val _finishWorldCup: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _winnerItem: MutableStateFlow<WorldCupListResponseItem> = MutableStateFlow(WorldCupListResponseItem())
    private val winners = mutableListOf<Long>()
    val videoList = _videoList.asStateFlow()
    val currentMatch = _currentMatch.asStateFlow()
    val finishWorldCup = _finishWorldCup.asStateFlow()
    val pagerState = _pagerState.asStateFlow()
    val winnerItem = _winnerItem.asStateFlow()

    init {
        getWorldCupList()
    }

    private fun getWorldCupList() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = videoRepository.getWorldCupList()
            _videoList.update { response }
        }
    }

    private fun updateVideoList() {
        viewModelScope.launch {
            _videoList.update {
                it.filter { video ->
                    winners.contains(video.id)
                }
            }
            _currentMatch.update { 0 to 1 }
            winners.clear()
        }
    }

    private fun nextMatch() {
        _currentMatch.value.let { (index1, index2) ->
            if (index2 + 2 < _videoList.value.size) {
                viewModelScope.launch { _currentMatch.update { index2 + 1 to index2 + 2 } }
            } else {
                if (_videoList.value.size == 2) {
                    val winnerId = winners[0]
                    val winnerVideoItem = _videoList.value.find { it.id == winnerId }
                    viewModelScope.launch { _winnerItem.update { winnerVideoItem!! } }
                    finishWorldCup()
                } else {
                    updateVideoList()
                }
            }
        }
    }

    fun selectWinner(winnerId: Long) {
        winners.add(winnerId)
        nextMatch()
    }

    private fun finishWorldCup() {
        viewModelScope.launch {
            _finishWorldCup.update { true }
            winners.clear()
        }
    }

    fun resetFinishWorldCup() {
        viewModelScope.launch { _finishWorldCup.update { false } }
    }
}