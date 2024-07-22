package com.example.domain.usecase

interface UseCase<in P, R> {
    suspend operator fun invoke(params: P): R
}