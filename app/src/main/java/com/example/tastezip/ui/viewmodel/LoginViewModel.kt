package com.example.tastezip.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezip.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    // LiveData를 사용하여 로그인 성공 여부를 관찰
    private val _loginSuccess : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess
    // LiveData를 사용하여 에러 메시지 전달
    private val _errorMessage : MutableStateFlow<String?> = MutableStateFlow("")
    val errorMessage: StateFlow<String?> = _errorMessage

    fun handleGoogleLoginResult(data: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            // Google 계정에서 얻은 idToken으로 Firebase 인증 정보 생성
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            // Firebase 인증 시도
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        // 로그인 성공
                        viewModelScope.launch {
                            _loginSuccess.update { true }
                        }
                    } else {
                        // 로그인 실패
                        viewModelScope.launch {
                            _loginSuccess.update { false }
                            _errorMessage.update { authTask.exception?.localizedMessage }
                        }
                    }
                }
        } catch (e: ApiException) {
            // Google 로그인 실패 처리
            viewModelScope.launch {
                _loginSuccess.update { false }
                _errorMessage.update { e.localizedMessage }
            }
        }
    }

    fun signInWithGoogle(context: Context, launcher: ActivityResultLauncher<Intent>) {
        val token = context.getString(R.string.client_id)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
        launcher.launch(googleSignInClient.signInIntent)
    }
}