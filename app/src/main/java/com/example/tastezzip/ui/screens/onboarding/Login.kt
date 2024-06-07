package com.example.tastezzip.ui.screens.onboarding

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.tastezzip.R
import com.example.tastezzip.navigation.NavRoutes
import com.example.tastezzip.ui.component.CustomText
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.LoginViewModel

@Composable
fun Login(navController: NavHostController, loginViewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        loginViewModel.handleGoogleLoginResult(it.data)
    }
    val loginSuccess = loginViewModel.loginSuccess.collectAsState()
    val errorMessage = loginViewModel.errorMessage.collectAsState()

    LaunchedEffect(loginSuccess.value) {
        Toast.makeText(context, errorMessage.value, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(key1 = true) {
        loginViewModel.loginEventFlow.collect {
            when (it) {
                true -> {
                    Toast.makeText(context, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    navController.navigate(NavRoutes.NaverMapScreen.route) {
                        popUpTo(NavRoutes.Login.route) {
                            inclusive = true
                        }
                    }
                }
                false -> {
                    Toast.makeText(context, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    navController.navigate(NavRoutes.UserInfo.route) {
                        popUpTo(NavRoutes.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Spacer(modifier = Modifier.size(100.dp))

            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "app_icon",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.size(40.dp))

            CustomText(
                text = "당신의 음식 취향 편집샵, 맛집",
                fontSize = 24.sp,
                font = Font(R.font.pretendard_bold),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                color = Color.Black
            )

            Spacer(modifier = Modifier.size(15.dp))

            CustomText(
                text = "Youtube Shorts 맛집 영상에 좋아요를 누르고\n" +
                        "다양한 음식점을 저장해보세요.",
                fontSize = 12.sp,
                font = Font(R.font.pretendard_medium),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)
            ) {

                IconButton(
                    onClick = {
                        loginViewModel.signInWithGoogle(context, launcher)
                    },
                    modifier = Modifier
                        .size(width = 250.dp, height = 48.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_google_login),
                        contentDescription = "btn_google_login"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    MainActivityTheme {
//        Login(LoginViewModel())
    }
}