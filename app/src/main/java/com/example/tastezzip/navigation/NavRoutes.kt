package com.example.tastezzip.navigation

sealed class NavRoutes(val route: String) {
    data object Splash: NavRoutes("splash")
    data object Login: NavRoutes("login")
    data object UserInfo: NavRoutes("userInfo")
    data object NaverMapScreen: NavRoutes("naverMapScreen")
    data object StoreShortsScreen: NavRoutes("storeShortsScreen") {
        fun createRoute(index: Int): String {
            return "${StoreShortsScreen.route}/$index"
        }
    }
    data object ShortsScreen: NavRoutes("shortsScreen")
    data object RecommendScreen: NavRoutes("recommendScreen")
    data object MyPageScreen: NavRoutes("myPage")
    data object BookmarkCafeteriaScreen: NavRoutes("bookmarkCafeteria")
    data object CafeteriaCommentScreen: NavRoutes("cafeteriaComment") {
        fun createRoute(id: Long): String {
            return "${CafeteriaCommentScreen.route}/$id"
        }
    }

    data object FoodWorldCupScreen: NavRoutes("foodWc")
    data object FoodWorldCupGame: NavRoutes("foodWcGame")
}