package com.example.tastezzip.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.data.repository.AccountRepository
import com.example.tastezzip.data.repository.CafeteriaRepository
import com.example.tastezzip.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    @ApplicationContext context: Context,
    loadingManager: LoadingManager
): BaseViewModel(loadingManager) {
    private val _nickName: MutableStateFlow<String> = MutableStateFlow("")
    private val _bio: MutableStateFlow<String> = MutableStateFlow("")
    private val _goToLoginSharedFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val nickname = _nickName.asStateFlow()
    val bio = _bio.asStateFlow()
    val goToLoginSharedFlow = _goToLoginSharedFlow.asSharedFlow()

    init {
        getAccount()
    }

    private fun getAccount() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response = accountRepository.getAccount()
                _nickName.update { response.account.nickname }
                _bio.update { response.account.bio }
            } catch (e: Exception) {
                Log.e("MyPage", e.toString())
            } finally {
                setLoading(false)
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            setLoading(true)
            try {
                accountRepository.deleteAccount()
                deleteAllAppData()
                _goToLoginSharedFlow.emit(true)
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