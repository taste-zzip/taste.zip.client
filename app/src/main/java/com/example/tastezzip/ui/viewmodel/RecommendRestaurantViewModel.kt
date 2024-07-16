package com.example.tastezzip.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.data.repository.CafeteriaRepository
import com.example.tastezzip.model.response.cafeteria.recommendation.RecommendResponseItem
import com.example.tastezzip.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendRestaurantViewModel @Inject constructor(
    private val cafeteriaRepository: CafeteriaRepository,
    loadingManager: LoadingManager
): BaseViewModel(loadingManager) {
    private val _recommendListStateFlow: MutableStateFlow<List<RecommendResponseItem>> = MutableStateFlow(emptyList())
    val recommendList = _recommendListStateFlow.asStateFlow()

    init {
        getRecommendationList()
    }

    fun getRecommendationList() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response = cafeteriaRepository.getRecommendationList()
                Log.e("recommendationList", response.toString())
                _recommendListStateFlow.update { response }
            } catch (e: Exception) {

            } finally {
                setLoading(false)
            }
        }
    }
}