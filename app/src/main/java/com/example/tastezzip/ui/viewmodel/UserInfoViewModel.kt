package com.example.tastezzip.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(): ViewModel() {
    private var nickname = ""
    private var oneLineInfo = ""

    fun saveUserInfo(nickname: String, oneLineInfo: String) {
        this.nickname = nickname
        this.oneLineInfo = oneLineInfo
    }
}