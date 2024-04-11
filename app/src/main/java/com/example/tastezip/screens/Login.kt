package com.example.tastezip.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import com.example.tastezip.R
import com.example.tastezip.navigation.NavRoutes
import com.example.tastezip.ui.theme.MainActivityTheme
import com.example.tastezip.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

@Composable
fun Login(navController: NavHostController, loginViewModel: LoginViewModel, context: Context) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        loginViewModel.login(activityResult = it) {
            Toast.makeText(context, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()

            navController.navigate(NavRoutes.UserInfo.route) {
                popUpTo(NavRoutes.Login.route) {
                    inclusive = true
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
                        val token = getString(context, R.string.client_id)

                        val googleSignInOptions = GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()

                        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
                        launcher.launch(googleSignInClient.signInIntent)
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

@Composable
fun CustomText(text: String, fontSize: TextUnit, font: Font, modifier: Modifier = Modifier, color: Color) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontFamily = FontFamily(font)
        ),
        color = color,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun LoginPreview() {
    MainActivityTheme {
//        Login(LoginViewModel())
    }
}