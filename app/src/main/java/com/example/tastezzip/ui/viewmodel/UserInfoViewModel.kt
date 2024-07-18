package com.example.tastezzip.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.data.repository.AuthRepository
import com.example.tastezzip.model.request.auth.registration.Oauth
import com.example.tastezzip.model.request.auth.registration.RegistrationRequestVo
import com.example.tastezzip.ui.base.BaseViewModel
import com.example.tastezzip.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext context: Context,
    loadingManager: LoadingManager,
    private val userInfo: UserInfo
): BaseViewModel(loadingManager) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val _registrationEventFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    private var nickname = ""
    private var oneLineInfo = ""
    val registrationEventFlow = _registrationEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            deleteAllAppData()
        }
    }

    fun saveUserInfo(nickname: String, oneLineInfo: String) {
        this.nickname = nickname
        this.oneLineInfo = oneLineInfo
    }

    fun registration() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response = authRepository.registration(request = RegistrationRequestVo(nickname = nickname, oauth = Oauth(code = userInfo.authCode)))
                Log.e("회원가입 결과", response.toString())
                sharedPreferences.edit().putString("accessToken", response.accessToken).apply()
                sharedPreferences.edit().putString("refreshToken", response.refreshToken).apply()
                Log.e("sharedPrefs", sharedPreferences.getString("refreshToken", null).toString())
                _registrationEventFlow.emit(true)
            } catch (e: Exception) {

            } finally {
                setLoading(false)
            }
        }
    }

    private suspend fun deleteAllAppData() {
        withContext(Dispatchers.Main) {
            sharedPreferences.edit().clear().apply()
        }
    }
}