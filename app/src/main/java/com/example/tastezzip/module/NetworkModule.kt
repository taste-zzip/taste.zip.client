package com.example.tastezzip.module

import com.example.tastezzip.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJkZXRhaWwiOnsidXNlcklkIjoxMX0sInR5cGUiOiJSRUZSRVNIX1RPS0VOIiwiaWF0IjoxNzE2MzgzNTk0LCJleHAiOjE3MTg4MDI3OTR9.1cqmzug6Wxe3-KzDgl4JRf8CjbLCLz2mjvoI9Mit93ApoN5JvANI5LbyE1XEAzYemrTchhqVpIum19SSaL4j8g"
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val headerInterceptor = Interceptor{
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            return@Interceptor it.proceed(request)
        }
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}