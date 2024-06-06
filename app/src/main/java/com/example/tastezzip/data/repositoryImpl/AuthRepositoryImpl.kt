package com.example.tastezzip.data.repositoryImpl

import com.example.tastezzip.data.api.AuthApi
import com.example.tastezzip.data.repository.AuthRepository
import com.example.tastezzip.model.request.auth.login.LoginRequestVo
import com.example.tastezzip.model.request.auth.registration.RegistrationRequestVo
import com.example.tastezzip.model.response.registration.RegistrationResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun registration(request: RegistrationRequestVo): RegistrationResponse {
        return authApi.registration(request)
    }
    override suspend fun login(request: LoginRequestVo): RegistrationResponse {
        return authApi.login(request)
    }
}