package com.example.tastezzip.data.repository

import com.example.tastezzip.model.response.account.AccountResponse

interface AccountRepository {
    suspend fun getAccount(): AccountResponse
    suspend fun deleteAccount()
}