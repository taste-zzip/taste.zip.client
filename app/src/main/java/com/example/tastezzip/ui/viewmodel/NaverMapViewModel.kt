package com.example.tastezip.ui.viewmodel

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezip.ui.screens.navermap.NaverItem
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(): ViewModel() {
    private val _searchData = MutableStateFlow("")
    val searchData: StateFlow<String> = _searchData.asStateFlow()

    private val _searchResult = MutableStateFlow<Result<List<LatLng>>>(Result.success(emptyList()))
    val searchResult: StateFlow<Result<List<LatLng>>> = _searchResult.asStateFlow()

    private val _latitude : MutableStateFlow<Double> = MutableStateFlow(0.0)
    val latitude : StateFlow<Double> = _latitude
    private val _longitude : MutableStateFlow<Double> = MutableStateFlow(0.0)
    val longitude : StateFlow<Double> = _longitude

    fun updateSearchData(newData: String) {
        _searchData.value = newData
    }

    fun getLatLang(name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            if (name == "바다회사랑") {
                val seoulCityHall = LatLng(37.56040942740714, 126.92106201288655)
                val dummyLatLngList = listOf(seoulCityHall)
                _searchResult.update {
                    Result.success(dummyLatLngList)
                }
            } else {
                _searchResult.value = Result.success(emptyList())
            }
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
}