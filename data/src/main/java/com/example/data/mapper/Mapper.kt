package com.example.data.mapper

interface Mapper<DTO, DOMAIN> {
    fun dtoToDomain(dto: DTO): DOMAIN
}