package com.example.tastezip

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tastezip.navigation.NavRoutes
import com.example.tastezip.ui.screens.onboarding.Login
import com.example.tastezip.ui.screens.onboarding.Splash
import com.example.tastezip.ui.screens.onboarding.UserInfo
import com.example.tastezip.ui.theme.MainActivityTheme
import com.example.tastezip.ui.viewmodel.LoginViewModel
import com.example.tastezip.ui.viewmodel.UserInfoViewModel
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tastezip.navigation.NavBarItems
import com.example.tastezip.ui.screens.navermap.BottomSheetLayout
import com.example.tastezip.ui.viewmodel.BottomSheetViewModel
import com.example.tastezip.ui.viewmodel.NaverMapViewModel

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
                    MainScreen(this)
                }
            }
        }
    }
}

@Composable
fun MainScreen(context: Context) {
    val bottomBarState = rememberSaveable { mutableStateOf(false) }

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
                NavigationHost(navController = navController, bottomBarState = bottomBarState)
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, bottomBarState: MutableState<Boolean>) {
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
            BottomSheetLayout()
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController, bottomBarState: MutableState<Boolean>) {

    AnimatedVisibility(
        visible = bottomBarState.value
    ) {
        var currentSelectedItem by remember { mutableStateOf(2) }

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
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
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