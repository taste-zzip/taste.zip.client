package com.example.tastezip.screens.navermap

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.tastezip.R
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen() {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()

    NaverMap(
        locationSource = rememberFusedLocationSource(),
        properties = MapProperties(
            locationTrackingMode = LocationTrackingMode.Follow,
        ),
        uiSettings = MapUiSettings(
            isLocationButtonEnabled = true,
        ),
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
    )
}