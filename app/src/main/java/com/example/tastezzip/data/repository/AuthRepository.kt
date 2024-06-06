package com.example.tastezzip.data.repository

import com.example.tastezzip.model.request.auth.login.LoginRequestVo
import com.example.tastezzip.model.request.auth.registration.RegistrationRequestVo
import com.example.tastezzip.model.response.registration.RegistrationResponse
import java.util.concurrent.Flow

interface AuthRepository {
    suspend fun registration(request: RegistrationRequestVo): RegistrationResponse
    suspend fun login(request: LoginRequestVo): RegistrationResponse
}