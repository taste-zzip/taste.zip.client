package com.example.tastezzip.ui.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.data.repository.CafeteriaRepository
import com.example.tastezzip.data.repository.YoutubeRepository
import com.example.tastezzip.model.request.BookmarkCafeteriaRequestVo
import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.request.SizeRequestVo
import com.example.tastezzip.model.response.cafeteria.Content
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.model.response.cafeteria.bookmark.Cafeteria
import com.example.tastezzip.model.response.youtube.LikedCafeteria
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    private val cafeteriaRepository: CafeteriaRepository,
    private val youtubeRepository: YoutubeRepository
): ViewModel() {
    private val _searchData: MutableStateFlow<List<Content>> = MutableStateFlow(emptyList())
    private val _searchResult = MutableStateFlow<Result<List<LatLng>>>(Result.success(emptyList()))
    private val _latitude : MutableStateFlow<Double> = MutableStateFlow(0.0)
    private val _longitude : MutableStateFlow<Double> = MutableStateFlow(0.0)
    private val _newBookmarkList: MutableStateFlow<List<LikedCafeteria>> = MutableStateFlow(emptyList())
    private val _searchKeyword: MutableStateFlow<String> = MutableStateFlow("")
    private val _bookmarkList: MutableStateFlow<List<Cafeteria>> = MutableStateFlow(emptyList())
    private val _searchSuccessEvent = MutableSharedFlow<Boolean>()
    val searchData: StateFlow<List<Content>> = _searchData.asStateFlow()
    val latitude : StateFlow<Double> = _latitude
    val longitude : StateFlow<Double> = _longitude
    val searchKeyword: StateFlow<String> = _searchKeyword.asStateFlow()
    val newBookmarkList: StateFlow<List<LikedCafeteria>> = _newBookmarkList.asStateFlow()
    val bookmarkList: StateFlow<List<Cafeteria>> = _bookmarkList.asStateFlow()
    val searchSuccessEvent = _searchSuccessEvent.asSharedFlow()

    init {
        getNewBookmarkList()
        getBookmarkList()
    }

    fun getNewBookmarkList() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = youtubeRepository.getYoutubeLikeCafeteriaList(SizeRequestVo(30))
                _newBookmarkList.update { response.likedCafeteria }
                _newBookmarkList.value.map {
                    cafeteriaRepository.bookmarkCafeteria(BookmarkCafeteriaRequestVo(it.id))
                }
            } catch (e: Exception) {
                Log.e("lllllllllllll", e.toString())
            }
        }
    }

    fun getBookmarkList() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = cafeteriaRepository.getBookmarkList()
                _bookmarkList.update { response.cafeteriaList }
            } catch (e: Exception) {

            }
        }
    }

    fun updateSearchKeyword(word: String) {
        viewModelScope.launch {
            _searchKeyword.update { word }
        }
    }

    fun updateUserLocation(location: Location) {
        viewModelScope.launch(Dispatchers.Main) {
            _latitude.update {
                location.latitude
            }
            _longitude.update {
                location.longitude
            }
        }
    }

    fun searchCafeteria(keyword: String) {
        viewModelScope.launch {
            val response = cafeteriaRepository.searchCafeteria(keyword, SearchCafeteriaRequest(10,3, listOf("name", "asc")))
            _searchData.update { response.content }
            _searchSuccessEvent.emit(true)
            Log.e("과연?", response.toString())
        }
    }
}