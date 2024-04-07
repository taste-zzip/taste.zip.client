package com.example.tastezip.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tastezip.R
import com.example.tastezip.navigation.NavRoutes
import com.example.tastezip.ui.theme.MainActivityTheme
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController) {
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
                modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
            )

            Image(
                painter = painterResource(id = R.drawable.splash_text),
                contentDescription = "splash_text",
                modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)

            )
        }
        
        LaunchedEffect(key1 = true) {
            delay(2000)
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
        Splash(rememberNavController())
    }
}