package com.example.tastezzip.navigation

import com.example.tastezzip.R

object NavBarItems {
    val barItems = listOf(
        BarItem(
            title = "음식 월드컵",
            icon = R.drawable.ic_food_wc,
            route = ""
        ),
        BarItem(
            title = "추천",
            icon = R.drawable.ic_thumb_up,
            route = "recommendScreen"
        ),
        BarItem(
            title = "홈",
            icon = R.drawable.ic_home,
            route = "naverMapScreen"
        ),
        BarItem(
            title = "Shorts",
            icon = R.drawable.ic_shorts,
            route = "shortsScreen"
        ),
        BarItem(
            title = "마이페이지",
            icon = R.drawable.ic_my_page,
            route = "myPage"
        )
    )
}