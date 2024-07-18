package com.example.tastezzip.ui.viewmodel

import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.application.MainApplication
import com.example.tastezzip.data.repository.CafeteriaRepository
import com.example.tastezzip.data.repository.TestCafeteriaRepository
import com.example.tastezzip.data.repository.YoutubeRepository
import com.example.tastezzip.model.request.BookmarkCafeteriaRequestVo
import com.example.tastezzip.model.request.SearchCafeteriaRequest
import com.example.tastezzip.model.request.SizeRequestVo
import com.example.tastezzip.model.response.cafeteria.Content
import com.example.tastezzip.model.response.cafeteria.SearchCafeteriaResponse
import com.example.tastezzip.model.response.cafeteria.bookmark.BookmarkListResponseItem
import com.example.tastezzip.model.response.cafeteria.bookmark.Cafeteria
import com.example.tastezzip.model.response.youtube.LikedCafeteria
import com.example.tastezzip.ui.base.BaseViewModel
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val youtubeRepository: YoutubeRepository,
    private val testCafeteriaRepository: TestCafeteriaRepository,
    loadingManager: LoadingManager,
    @ApplicationContext context: Context
): BaseViewModel(loadingManager) {
    private val _searchData: MutableStateFlow<List<Content>> = MutableStateFlow(emptyList())
    private val _latitude : MutableStateFlow<Double> = MutableStateFlow( 0.0)
    private val _longitude : MutableStateFlow<Double> = MutableStateFlow(0.0 )
    private val _newBookmarkList: MutableStateFlow<List<LikedCafeteria>> = MutableStateFlow(emptyList())
    private val _searchKeyword: MutableStateFlow<String> = MutableStateFlow("")
    private val _bookmarkList: MutableStateFlow<List<BookmarkListResponseItem>> = MutableStateFlow(emptyList())
    private val _searchSuccessEvent = MutableSharedFlow<Boolean>()
    private val _completeAddBookmark = MutableStateFlow(false)
    private val _showAddBookmarkDialog = MutableStateFlow(false)
    val searchData: StateFlow<List<Content>> = _searchData.asStateFlow()
    val latitude : StateFlow<Double> = _latitude
    val longitude : StateFlow<Double> = _longitude
    val searchKeyword: StateFlow<String> = _searchKeyword.asStateFlow()
    val newBookmarkList: StateFlow<List<LikedCafeteria>> = _newBookmarkList.asStateFlow()
    val bookmarkList: StateFlow<List<BookmarkListResponseItem>> = _bookmarkList.asStateFlow()
    val searchSuccessEvent = _searchSuccessEvent.asSharedFlow()
    val completeAddBookmark = _completeAddBookmark.asStateFlow()
    val showAddBookmarkDialog = _showAddBookmarkDialog.asStateFlow()

    init {
        getBookmarkList()
        initUserLocation(37.56300460476657, 126.92152333229693)
    }

    fun getNewBookmarkList() {
        viewModelScope.launch(Dispatchers.Main) {
            setLoading(true)
            try {
                val response = youtubeRepository.getYoutubeLikeCafeteriaList(SizeRequestVo(30))
                if (response.likedCafeteria.isNotEmpty()) _showAddBookmarkDialog.update { true }
                _newBookmarkList.update { response.likedCafeteria }
            } catch (e: Exception) {
                Log.e("getNewBookmarkList", e.toString())
            } finally {
                setLoading(false)
            }
        }
    }

    fun addNewBookmark() {
        viewModelScope.launch {
            setLoading(true)
            try {
                _newBookmarkList.value.forEach {
                    cafeteriaRepository.bookmarkCafeteria(BookmarkCafeteriaRequestVo(it.id))
                }
                _completeAddBookmark.update { true }
                getBookmarkList()
            } catch (e: Exception) {
                Log.e("addBookmark", e.toString())
            } finally {
                setLoading(false)
            }
        }
    }

     fun getBookmarkList() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response = cafeteriaRepository.getBookmarkList()
                _bookmarkList.update { response.cafeteriaList }
                Log.e("bookmarkList", response.toString())
            } catch (e: Exception) {
                Log.e("akwvbiaerubgiae", e.toString())
            } finally {
                setLoading(false)
            }
        }
    }

    fun updateSearchKeyword(word: String) {
        viewModelScope.launch {
            _searchKeyword.update { word }
        }
    }

    fun initUserLocation(lat: Double, long: Double) {
        viewModelScope.launch(Dispatchers.Main) {
            _latitude.update {
                lat
            }
            _longitude.update {
                long
            }
        }
    }

//    fun searchCafeteria(keyword: String) {
//        viewModelScope.launch {
//            setLoading(true)
//            try {
//                val response = cafeteriaRepository.searchCafeteria(keyword, SearchCafeteriaRequest(10,3, listOf("name", "asc")))
//                _searchData.update { response.content }
//                _searchSuccessEvent.emit(true)
//            } catch (e: Exception) {
//                Log.e("searchCafeteria", e.toString())
//            } finally {
//                setLoading(false)
//            }
//        }
//    }

    fun searchCafeteria(keyword: String) {
        viewModelScope.launch {
            testCafeteriaRepository.searchCafeteria(keyword, SearchCafeteriaRequest(10,3, listOf("name", "asc"))).collect { it ->
                Log.e("collect 내부 결과", it.toString())
                handleResult(result = it, onSuccess = { onSuccessSearchCafeteria(it) })
            }
        }
    }

    private fun onSuccessSearchCafeteria(data: SearchCafeteriaResponse) {
        viewModelScope.launch {
            _searchData.update { data.content }
            Log.e("검색 리스트", _searchData.toString())
            _searchSuccessEvent.emit(true)
        }
    }

    fun resetCompleteAddBookmark() {
        _completeAddBookmark.update { false }
    }

    fun resetShowAddBookmarkDialog() {
        _showAddBookmarkDialog.update { false }
    }
}