package com.example.tastezzip.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.data.repository.CafeteriaRepository
import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.request.bookmark.DeleteBookmarkRequestVo
import com.example.tastezzip.model.response.cafeteria.bookmark.BookmarkListResponseItem
import com.example.tastezzip.model.response.cafeteria.recommendation.RecommendResponseItem
import com.example.tastezzip.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkCafeteriaViewModel @Inject constructor(
    private val cafeteriaRepository: CafeteriaRepository,
    loadingManager: LoadingManager
): BaseViewModel(loadingManager) {
    private val _bookmarkList: MutableStateFlow<List<BookmarkListResponseItem>> = MutableStateFlow(emptyList())
    val bookmarkList = _bookmarkList.asStateFlow()

    init {
        getBookmark()
    }

    fun getBookmark() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response = cafeteriaRepository.getBookmarkList()
                _bookmarkList.update { response.cafeteriaList }
            } catch (e: Exception) {
                Log.e("getBookmark BookmarkCafeteriaViewModel", e.toString())
            } finally {
                setLoading(false)
            }
        }
    }

    fun deleteBookmark(id: Long) {
        viewModelScope.launch {
            setLoading(true)
            try {
                cafeteriaRepository.deleteBookmark(DeleteBookmarkRequestVo(id, LikeType.LIKE))
                getBookmark()
            } catch (e: Exception) {
                Log.e("deleteBookmark", e.toString())
            } finally {
                setLoading(false)
            }
        }
    }
}