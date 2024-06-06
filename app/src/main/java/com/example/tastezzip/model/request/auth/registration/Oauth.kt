package com.example.tastezzip.model.request.auth.registration

import com.example.tastezzip.model.enums.OauthType

data class Oauth(
    val code: String,
    val type: OauthType = OauthType.GOOGLE
)