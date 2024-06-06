package com.example.tastezzip.data.api

import com.example.tastezzip.model.enums.SocialType
import com.example.tastezzip.model.request.auth.login.LoginRequestVo
import com.example.tastezzip.model.request.auth.registration.RegistrationRequestVo
import com.example.tastezzip.model.response.registration.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST(Endpoints.Auth.REGISTRATION)
    suspend fun registration(@Body request: RegistrationRequestVo): RegistrationResponse
    @POST(Endpoints.Auth.LOGIN)
    suspend fun login(@Body request: LoginRequestVo): RegistrationResponse
}