package com.example.tastezzip.data.repositoryImpl

import com.example.tastezzip.data.api.AuthApi
import com.example.tastezzip.data.repository.AuthRepository
import com.example.tastezzip.model.response.googleLogin.GoogleLoginUrlResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun getGoogleLoginUrl(): GoogleLoginUrlResponse {
        return authApi.getGoogleLoginUrl()
    }
}