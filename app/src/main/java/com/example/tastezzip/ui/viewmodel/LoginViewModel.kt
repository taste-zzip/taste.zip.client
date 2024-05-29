package com.example.tastezzip.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.data.repository.AuthRepository
import com.example.tastezzip.model.response.googleLogin.GoogleLoginUrlResponse
import com.example.tastezzip.util.GOOGLE_CLIENT_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _loginSuccess : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess
    private val _errorMessage : MutableStateFlow<String?> = MutableStateFlow("")
    val errorMessage: StateFlow<String?> = _errorMessage

    fun handleGoogleLoginResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val authCode = account.serverAuthCode
            val name = account.givenName
            val email = account.email

            if (authCode != null) {
                Log.e("토큰", authCode)
                viewModelScope.launch {
                    _loginSuccess.update { true }
                }
//                requestLogin(authCode)
            }
        } catch (e: ApiException) {
            viewModelScope.launch {
                _loginSuccess.update { false }
                _errorMessage.update { e.localizedMessage }
            }
        }
    }

    fun signInWithGoogle(context: Context, launcher: ActivityResultLauncher<Intent>) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(GOOGLE_CLIENT_ID)
            .requestIdToken(GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
        launcher.launch(googleSignInClient.signInIntent)
    }
}