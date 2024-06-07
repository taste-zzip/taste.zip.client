package com.example.tastezzip.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastezzip.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val accountRepository: AccountRepository
): ViewModel() {
    private val _nickName: MutableStateFlow<String> = MutableStateFlow("")
    private val _bio: MutableStateFlow<String> = MutableStateFlow("")
    val nickname = _nickName.asStateFlow()
    val bio = _bio.asStateFlow()

    init {
        getAccount()
    }

    private fun getAccount() {
        viewModelScope.launch {
            try {
                val response = accountRepository.getAccount()
                _nickName.update { response.account.nickname }
                _bio.update { response.account.bio }
            } catch (e: Exception) {
                Log.e("MyPage", e.toString())
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            accountRepository.deleteAccount()
        }
    }
}