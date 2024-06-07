package com.example.tastezzip.model.response.account

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("account")
    val account: Account
)