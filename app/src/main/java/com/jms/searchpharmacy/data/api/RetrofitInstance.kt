package com.jms.searchpharmacy.data.api

import com.jms.searchpharmacy.data.api.server.ServerApi


import com.jms.searchpharmacy.util.Constants.NAVERMAP_BASE_URL
import com.jms.searchpharmacy.util.Constants.SERVER_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val okHttpClient: OkHttpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun buildRetrofit(base_url: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(base_url)
            .build()
    }


    val naverMapApi: NaverMapSearchApi by lazy {
        buildRetrofit(NAVERMAP_BASE_URL).create(NaverMapSearchApi::class.java)
    }


    val serverApi: ServerApi by lazy {
        buildRetrofit(SERVER_BASE_URL).create(ServerApi::class.java)
    }
}