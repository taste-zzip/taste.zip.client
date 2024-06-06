package com.example.tastezzip.model.response.registration

data class RegistrationResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenDetail: TokenDetail
)