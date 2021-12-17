package com.app.bluetask.client

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit

import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class ApiClient {

    private val TAG = ApiClient::class.java.simpleName
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .callTimeout(60, TimeUnit.SECONDS)
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.coincap.io/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}