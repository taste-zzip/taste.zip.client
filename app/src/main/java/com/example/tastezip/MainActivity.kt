package com.example.tastezip

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.tastezip.screens.Login
import com.example.tastezip.screens.navermap.NaverMapScreen
import com.example.tastezip.screens.Splash
import com.example.tastezip.screens.UserInfo
import com.example.tastezip.ui.theme.MainActivityTheme
import com.example.tastezip.viewmodel.LoginViewModel
import com.example.tastezip.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    val navController = rememberNavController()

    NavigationHost(navController = navController, context)
}

@Composable
fun NavigationHost(navController: NavHostController, context: Context) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        composable(NavRoutes.Splash.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            Splash(navController, loginViewModel)
        }

        composable(NavRoutes.Login.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            Login(navController, loginViewModel)
        }

        composable(NavRoutes.UserInfo.route) {
            val userInfoViewModel = hiltViewModel<UserInfoViewModel>()
            UserInfo(navController, userInfoViewModel)
        }

        composable(NavRoutes.NaverMapScreen.route) {
            NaverMapScreen()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainActivityTheme {
//        MainScreen()
    }
}