package com.example.tastezip.screens.navermap

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.tastezip.R
import com.example.tastezip.viewmodel.NaverMapViewModel
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.DisposableMapEffect
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import ted.gun0912.clustering.naver.TedNaverClustering

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(viewModel: NaverMapViewModel, onMarkerClick: () -> Unit) {
    val context = LocalContext.current
    val searchData by viewModel.searchData.collectAsState()
    val searchResult by viewModel.searchResult.collectAsState()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var latitude = 37.50358609454666
    var longitude = 126.95705499070183
    val keyboardController = LocalSoftwareKeyboardController.current


    val cameraPositionState = rememberCameraPositionState{ CameraPosition(LatLng(latitude, longitude), 10.0) }
    LaunchedEffect(latitude, longitude) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@LaunchedEffect
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
                .addOnFailureListener { e ->
                    // 위치 정보 가져오기 실패 처리
                }
            cameraPositionState.move(CameraUpdate.toCameraPosition(CameraPosition(LatLng(latitude, longitude), 12.0)))
        }
    }

    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 50.0,
                minZoom = 10.0,
                locationTrackingMode = LocationTrackingMode.Follow
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NaverMap(
            locationSource = rememberFusedLocationSource(isCompassEnabled = true),
            properties = mapProperties,
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true,
            ),
            cameraPositionState = cameraPositionState,
        ) {
            searchResult.getOrNull()?.forEach { latLng ->
                Marker(
                    state = MarkerState(position = latLng),
                    onClick = {
                        onMarkerClick()
                        true
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 10.dp)
                .padding(top = 20.dp)
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .shadow(
                    elevation = 5.dp, // 그림자 높이
                    shape = RoundedCornerShape(10.dp), // 그림자 모양
                    ambientColor = Color.Gray
                )
        ) {
            TextField(
                value = searchData,
                onValueChange = { newData -> viewModel.updateSearchData(newData) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("찾고 싶은 음식점을 검색해보세요.") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search"
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.getLatLang(searchData)
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            LaunchedEffect(key1 = searchResult) {
                val firstLatLng: LatLng? = searchResult.getOrNull()?.firstOrNull()
                if (firstLatLng != null) {
                    cameraPositionState.position = CameraPosition(firstLatLng, 15.0)
                    cameraPositionState.move(CameraUpdate.zoomIn())
                }
            }
        }
    }
}