package com.example.tastezzip.module

import android.content.Context
import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.application.MainApplication
import com.example.tastezzip.util.UserInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): MainApplication {
        return app as MainApplication
    }

    @Provides
    @Singleton
    fun provideLoadingManager(): LoadingManager {
        return LoadingManager()
    }

    @Provides
    @Singleton
    fun provideUserInfo(): UserInfo {
        return UserInfo()
    }
}