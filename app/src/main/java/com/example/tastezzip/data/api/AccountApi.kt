package com.example.tastezzip.data.api

import retrofit2.http.DELETE

interface AccountApi {
    @DELETE(Endpoints.Account.ACCOUNT)
    suspend fun deleteAccount()
}