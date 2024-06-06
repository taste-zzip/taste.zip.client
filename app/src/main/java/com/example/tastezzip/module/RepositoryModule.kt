package com.example.tastezzip.module

import com.example.tastezzip.data.repository.AccountRepository
import com.example.tastezzip.data.repository.AuthRepository
import com.example.tastezzip.data.repository.CafeteriaRepository
import com.example.tastezzip.data.repository.VideoRepository
import com.example.tastezzip.data.repository.YoutubeRepository
import com.example.tastezzip.data.repositoryImpl.AccountRepositoryImpl
import com.example.tastezzip.data.repositoryImpl.AuthRepositoryImpl
import com.example.tastezzip.data.repositoryImpl.CafeteriaRepositoryImpl
import com.example.tastezzip.data.repositoryImpl.VideoRepositoryImpl
import com.example.tastezzip.data.repositoryImpl.YoutubeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun providesAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun providesCafeteriaRepository(repositoryImpl: CafeteriaRepositoryImpl): CafeteriaRepository

    @Singleton
    @Binds
    abstract fun providesVideoRepository(repositoryImpl: VideoRepositoryImpl): VideoRepository

    @Singleton
    @Binds
    abstract fun providesYoutubeRepository(repositoryImpl: YoutubeRepositoryImpl): YoutubeRepository

    @Singleton
    @Binds
    abstract fun providesAccountRepository(repositoryImpl: AccountRepositoryImpl): AccountRepository
}