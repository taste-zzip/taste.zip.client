package com.example.tastezzip.data.repositoryImpl

import com.example.tastezzip.data.api.AccountApi
import com.example.tastezzip.data.repository.AccountRepository
import com.example.tastezzip.model.response.account.AccountResponse
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountApi
) : AccountRepository {
    override suspend fun getAccount(): AccountResponse {
        return accountApi.getAccount()
    }
    override suspend fun deleteAccount() {
        return accountApi.deleteAccount()
    }
}