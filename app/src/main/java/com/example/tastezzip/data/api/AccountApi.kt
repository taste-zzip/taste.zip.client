package com.example.tastezzip.data.api

import com.example.tastezzip.model.response.account.AccountResponse
import retrofit2.http.DELETE
import retrofit2.http.GET

interface AccountApi {
    @GET(Endpoints.Account.ACCOUNT)
    suspend fun getAccount(): AccountResponse
    @DELETE(Endpoints.Account.ACCOUNT)
    suspend fun deleteAccount()
}