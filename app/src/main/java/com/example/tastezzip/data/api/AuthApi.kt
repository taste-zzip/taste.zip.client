package com.example.tastezzip.data.api

import com.example.tastezzip.model.enums.SocialType
import com.example.tastezzip.model.response.googleLogin.GoogleLoginUrlResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {
    @GET(Endpoints.Auth.GOOGLE)
    suspend fun getGoogleLoginUrl(
        @Query("type") type: String = "GOOGLE"
    ): GoogleLoginUrlResponse
}