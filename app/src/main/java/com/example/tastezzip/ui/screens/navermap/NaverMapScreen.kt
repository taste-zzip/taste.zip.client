package com.example.tastezzip.ui.screens.navermap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tastezzip.R
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.NaverMapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerDefaults
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(viewModel: NaverMapViewModel, onMarkerClick: (Long) -> Unit) {
    val context = LocalContext.current
    val searchData by viewModel.searchData.collectAsState()
    val searchKeyword by viewModel.searchKeyword.collectAsState()
    val latitude by viewModel.latitude.collectAsState()
    val longitude by viewModel.longitude.collectAsState()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val keyboardController = LocalSoftwareKeyboardController.current
    val cameraPositionState = rememberCameraPositionState{ CameraPosition(LatLng(latitude, longitude), 10.0) }
    var isInitialPositionSet by remember { mutableStateOf(false) }
    val bookmarkList by viewModel.bookmarkList.collectAsState()
    val customIcon = OverlayImage.fromResource(R.drawable.ic_bookmarked)



    LaunchedEffect(Unit) {
        if (!isInitialPositionSet) {
            initUserPosition(context, fusedLocationClient, viewModel)
            isInitialPositionSet = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.searchSuccessEvent.collect { isSuccess ->
            if (isSuccess) {
                val first = searchData.firstOrNull()

                if (first != null) {
                    val firstLatLng = LatLng(first.latitude.toDouble(), first.longitude.toDouble())
                    updateCameraPosition(cameraPositionState, firstLatLng, 15.0)
                }
            }
        }
    }

    LaunchedEffect(latitude, longitude) {
        updateCameraPosition(cameraPositionState, LatLng(latitude, longitude), 12.0)
    }

    val mapProperties by remember {
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
            searchData.forEach {
                val id = it.id
                Marker(
                    state = MarkerState(position = LatLng(it.latitude.toDouble(), it.longitude.toDouble())),
                    onClick = {
                        onMarkerClick(id)
                        true
                    }
                )
            }

            bookmarkList.forEach {
                val id = it.id
                Marker(
                    state = MarkerState(position = LatLng(it.latitude.toDouble(), it.longitude.toDouble())),
                    onClick = {
                        onMarkerClick(id)
                        true
                    },
                    icon = customIcon
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
                value = searchKeyword,
                onValueChange = { newData -> viewModel.updateSearchKeyword(newData) },
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
                        viewModel.searchCafeteria(searchKeyword)
                        updateCameraPosition(cameraPositionState, LatLng(latitude, longitude), 12.0)
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }

        IconButton(
            onClick = {
                viewModel.getNewBookmarkList()
                viewModel.getBookmarkList()
            },
            modifier = Modifier
                .size(80.dp, 80.dp)
                .align(Alignment.BottomEnd)
                .padding(15.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Unspecified,
                contentColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "", tint = Color.Unspecified,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

fun initUserPosition(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: NaverMapViewModel
) {
    if (
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    } else {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    viewModel.updateUserLocation(location)
                }
            }
            .addOnFailureListener { e ->
                // 위치 정보 가져오기 실패 처리
            }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
fun updateCameraPosition(cameraPositionState: CameraPositionState, latLng: LatLng, zoom: Double) {
    cameraPositionState.position = CameraPosition(latLng, zoom)
    cameraPositionState.move(CameraUpdate.zoomIn())
}