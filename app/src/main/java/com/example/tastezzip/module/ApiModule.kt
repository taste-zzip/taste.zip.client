package com.example.tastezzip.module

import com.example.tastezzip.data.api.AccountApi
import com.example.tastezzip.data.api.AuthApi
import com.example.tastezzip.data.api.CafeteriaApi
import com.example.tastezzip.data.api.VideoApi
import com.example.tastezzip.data.api.YoutubeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCafeteriaApi(
        retrofit: Retrofit
    ): CafeteriaApi {
        return retrofit.create(CafeteriaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideVideoApi(
        retrofit: Retrofit
    ): VideoApi {
        return retrofit.create(VideoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideYoutubeApi(
        retrofit: Retrofit
    ): YoutubeApi {
        return retrofit.create(YoutubeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAccountApi(
        retrofit: Retrofit
    ): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }
}