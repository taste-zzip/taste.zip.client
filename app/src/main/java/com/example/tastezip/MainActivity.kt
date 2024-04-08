package com.example.tastezip

import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tastezip.navigation.NavRoutes
import com.example.tastezip.screens.Login
import com.example.tastezip.screens.Splash
import com.example.tastezip.ui.theme.MainActivityTheme
import com.example.tastezip.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

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
    val loginViewModel = LoginViewModel()

    NavigationHost(navController = navController, loginViewModel = loginViewModel, context)
}

@Composable
fun NavigationHost(navController: NavHostController, loginViewModel: LoginViewModel, context: Context) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        composable(NavRoutes.Splash.route) {
            Splash(navController, loginViewModel)
        }

        composable(NavRoutes.Login.route) {
            Login(navController, loginViewModel, context)
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