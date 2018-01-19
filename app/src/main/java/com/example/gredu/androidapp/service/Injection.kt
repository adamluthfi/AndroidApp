package com.example.gredu.androidapp.service

import com.example.gredu.androidapp.BuildConfig
import com.example.gredu.androidapp.Constant
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Injection{
    companion object {
        fun getOkHttpClient(): OkHttpClient{
            val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG)
                logger.level = HttpLoggingInterceptor.Level.BODY
            else
                logger.level = HttpLoggingInterceptor.Level.NONE

            val okHttpClient : OkHttpClient =
                    OkHttpClient.Builder()
                            .addInterceptor(logger)
                            .addNetworkInterceptor(StethoInterceptor())
                            .build()
            return okHttpClient
        }

        fun provideRetrovit():Retrofit{
            val httpClient = getOkHttpClient()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()
            return retrofit
        }

        fun provideApiService():ApiService{
            return provideRetrovit().create(ApiService::class.java)
        }
    }
}