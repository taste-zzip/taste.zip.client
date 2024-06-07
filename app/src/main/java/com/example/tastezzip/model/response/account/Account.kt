package com.example.tastezzip.model.response.account

data class Account(
    val bio: String,
    val createdAt: String,
    val id: Long,
    val nickname: String,
    val profileImage: String,
    val type: String,
    val updatedAt: String
)