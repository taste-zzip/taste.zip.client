package com.example.tastezip.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.tastezip.R

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
            route = ""
        ),
        BarItem(
            title = "홈",
            icon = R.drawable.ic_home,
            route = "naverMapScreen"
        ),
        BarItem(
            title = "Shorts",
            icon = R.drawable.ic_shorts,
            route = ""
        ),
        BarItem(
            title = "마이페이지",
            icon = R.drawable.ic_my_page,
            route = ""
        )
    )
}