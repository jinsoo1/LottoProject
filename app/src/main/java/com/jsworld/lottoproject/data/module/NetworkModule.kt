package com.jsworld.lottoproject.data.module

import androidx.databinding.ktx.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jsworld.lottoproject.data.remote.api.LottoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://www.dhlottery.co.kr/"

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder() : Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRxJava2CallAdapterFactory() : RxJava2CallAdapterFactory{
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory() : GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {

        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .client(provideOkHttpClient())
            .addConverterFactory(provideGsonConverterFactory())
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : LottoApi {
        return retrofit.create(LottoApi::class.java)
    }






}