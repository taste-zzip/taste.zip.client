package com.example.tastezzip.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.data.repository.AccountRepository
import com.example.tastezzip.data.repository.AuthRepository
import com.example.tastezzip.model.request.auth.login.LoginRequestVo
import com.example.tastezzip.util.GOOGLE_CLIENT_ID
import com.example.tastezzip.util.UserInfo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    @ApplicationContext context: Context
): ViewModel() {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val _loginSuccess : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _errorMessage : MutableStateFlow<String?> = MutableStateFlow("")
    private val _loginEventFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    private var refreshToken: String? = null
    val loginSuccess: StateFlow<Boolean> = _loginSuccess
    val errorMessage: StateFlow<String?> = _errorMessage
    val loginEventFlow = _loginEventFlow.asSharedFlow()

    init {
        getAccessToken()
    }

    fun handleGoogleLoginResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val authCode = account.serverAuthCode
            val name = account.givenName
            val email = account.email

            if (authCode != null) {
                UserInfo.authCode = authCode
                Log.e("토큰", authCode)
                viewModelScope.launch {
                    if (sharedPreferences.getString("accessToken", null) == null) {
                        _loginEventFlow.emit(false)
                    } else {
                        val response = authRepository.login(request = LoginRequestVo(authCode))
                        Log.e("로그인 결과", response.toString())
                        sharedPreferences.edit().putString("accessToken", response.accessToken).apply()
                        sharedPreferences.edit().putString("refreshToken", response.refreshToken).apply()
                        Log.e("sharedPrefs", sharedPreferences.getString("accessToken", "").toString())
                        _loginEventFlow.emit(true)
                    }
                }
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
            .requestScopes(Scope("https://www.googleapis.com/auth/youtube"))
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
        launcher.launch(googleSignInClient.signInIntent)
    }

    private fun getAccessToken() {
        refreshToken = sharedPreferences.getString("refreshToken", null)
        Log.e("accessToken", refreshToken.toString())
    }

    fun hasAccessToken(): Boolean {
        return !refreshToken.isNullOrEmpty()
    }
}