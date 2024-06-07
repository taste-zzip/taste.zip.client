package com.example.tastezzip.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tastezzip.R
import com.example.tastezzip.navigation.NavRoutes
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController, loginViewModel: LoginViewModel = hiltViewModel()) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.splash)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "app_icon",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Image(
                painter = painterResource(id = R.drawable.splash_text),
                contentDescription = "splash_text",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)

            )
        }
        
        LaunchedEffect(key1 = true) {
            delay(2000)
//            if (loginViewModel.hasAccessToken()) {
//                navController.navigate(NavRoutes.NaverMapScreen.route) {
//                    popUpTo(NavRoutes.Splash.route) {
//                        inclusive = true
//                    }
//                }
//            } else {
//                navController.navigate(NavRoutes.Login.route) {
//                    popUpTo(NavRoutes.Splash.route) {
//                        inclusive = true
//                    }
//                }
//            }
            navController.navigate(NavRoutes.Login.route) {
                popUpTo(NavRoutes.Splash.route) {
                    inclusive = true
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashPreview() {
    MainActivityTheme {
//        Splash(rememberNavController())
    }
}