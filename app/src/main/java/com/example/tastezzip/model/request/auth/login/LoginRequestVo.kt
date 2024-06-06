package com.example.tastezzip.model.request.auth.login

import com.example.tastezzip.model.enums.OauthType

data class LoginRequestVo(
    val code: String,
    val type: OauthType = OauthType.GOOGLE
)