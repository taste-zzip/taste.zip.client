package com.example.tastezzip.data.repository

import com.example.tastezzip.model.response.googleLogin.GoogleLoginUrlResponse
import java.util.concurrent.Flow

interface AuthRepository {
    suspend fun getGoogleLoginUrl(): GoogleLoginUrlResponse
}