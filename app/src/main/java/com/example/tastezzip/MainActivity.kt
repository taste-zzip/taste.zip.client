package com.example.tastezzip

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tastezzip.navigation.NavRoutes
import com.example.tastezzip.ui.screens.onboarding.Login
import com.example.tastezzip.ui.screens.onboarding.Splash
import com.example.tastezzip.ui.screens.onboarding.UserInfo
import com.example.tastezzip.ui.theme.MainActivityTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.tastezzip.navigation.NavBarItems
import com.example.tastezzip.ui.screens.mypage.BookmarkCafeteria
import com.example.tastezzip.ui.screens.mypage.MyPageScreen
import com.example.tastezzip.ui.screens.navermap.BottomSheetLayout
import com.example.tastezzip.ui.screens.navermap.commet.CafeteriaCommentScreen
import com.example.tastezzip.ui.screens.recommend.RecommendRestaurant
import com.example.tastezzip.ui.screens.shorts.ShortsScreen
import com.example.tastezzip.ui.screens.shorts.ShortsTapScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val bottomBarState = rememberSaveable { mutableStateOf(false) }
    val isShorts = remember { mutableStateOf(false) }

    MainActivityTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        when (navBackStackEntry?.destination?.route) {
            "splash" -> {
                bottomBarState.value = false
            }
            "login" -> {
                bottomBarState.value = false
            }
            "userInfo" -> {
                bottomBarState.value = false
            }
            "naverMapScreen" -> {
                bottomBarState.value = true
            }
        }

        Scaffold(
            bottomBar = {
                BottomNavBar(navController = navController, bottomBarState = bottomBarState)
            },
        ) { paddingValue ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValue.calculateTopPadding(),
                        bottom = paddingValue.calculateBottomPadding()
                    )
            ) {
                NavigationHost(navController = navController, bottomBarState = bottomBarState, isShorts)
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, bottomBarState: MutableState<Boolean>, isShorts: MutableState<Boolean>) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        composable(NavRoutes.Splash.route) {
            LaunchedEffect(Unit) { bottomBarState.value = false }
            Splash(navController)
        }

        composable(NavRoutes.Login.route) {
            LaunchedEffect(Unit) { bottomBarState.value = false }
            Login(navController)
        }

        composable(NavRoutes.UserInfo.route) {
            LaunchedEffect(Unit) { bottomBarState.value = false }
            UserInfo(navController)
        }

        composable(NavRoutes.NaverMapScreen.route) {
            LaunchedEffect(Unit) { bottomBarState.value = true }
            BottomSheetLayout(isShorts = isShorts, navController = navController)
        }

        composable(NavRoutes.StoreShortsScreen.route) {
            LaunchedEffect(Unit) { bottomBarState.value = false }
            ShortsScreen(lifecycleOwner = LocalLifecycleOwner.current)
        }

        composable(NavRoutes.ShortsScreen.route) {
            LaunchedEffect(Unit) { bottomBarState.value = true }
            ShortsTapScreen(lifecycleOwner = LocalLifecycleOwner.current)
        }

        composable(NavRoutes.RecommendScreen.route) {
            LaunchedEffect(Unit) { bottomBarState.value = true }
            RecommendRestaurant()
        }

        composable(NavRoutes.MyPageScreen.route) {
            val onClickBookmarkCafeteria = {
                navController.navigate(NavRoutes.BookmarkCafeteriaScreen.route)
            }
            LaunchedEffect(Unit) { bottomBarState.value = true }
            MyPageScreen(onClickBookmarkCafeteria)
        }

        composable(NavRoutes.BookmarkCafeteriaScreen.route) {
            LaunchedEffect(key1 = Unit) { bottomBarState.value = true }
            BookmarkCafeteria {
                navController.popBackStack()
            }
        }

        composable(
            "${NavRoutes.CafeteriaCommentScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            LaunchedEffect(key1 = Unit) { bottomBarState.value = true }
            CafeteriaCommentScreen(cafeteriaId = id)
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController, bottomBarState: MutableState<Boolean>) {

    AnimatedVisibility(
        visible = bottomBarState.value
    ) {
        var currentSelectedItem by remember { mutableIntStateOf(2) }

        NavigationBar(
            containerColor = Color.White,
            contentColor = Color(0xFF3F414E)
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            NavBarItems.barItems.forEachIndexed { index, navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        if (currentSelectedItem != index) {
                            currentSelectedItem = index
                            navController.navigate(navItem.route) {
                                popUpTo(currentRoute!!) {
                                    saveState = true
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                                anim {
                                    enter = 0
                                    exit = 0
                                    popEnter = 0
                                    popExit = 0
                                }
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = navItem.icon),
                            contentDescription = navItem.title
                        )
                    },
                    label = {
                        Text(
                            text = navItem.title, style = TextStyle(
                                fontSize = 8.sp
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Black,
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainActivityTheme {
//        NaverMapScreen(viewModel())
    }
}