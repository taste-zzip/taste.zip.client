package com.example.tastezip.navigation

sealed class NavRoutes(val route: String) {
    data object Splash: NavRoutes("splash")
    data object Login: NavRoutes("login")
    data object UserInfo: NavRoutes("userInfo")

    data object NaverMapScreen: NavRoutes("naverMapScreen")
}