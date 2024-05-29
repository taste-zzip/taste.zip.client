package com.example.tastezzip.model.response.cafeteria.bookmark

data class Cafeteria(
    val city: String,
    val district: String,
    val id: Long,
    val landAddress: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val neighborhood: String,
    val streetAddress: String,
    val type: String
)