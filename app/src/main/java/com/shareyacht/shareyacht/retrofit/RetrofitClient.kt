package com.shareyacht.shareyacht.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        client.addInterceptor(interceptor)
        client.addInterceptor(Interceptor { chain ->
            val builder: Request.Builder = chain.request().newBuilder()
            builder.addHeader("content-type", "application/json")

            chain.proceed(builder.build())
        })

        if (retrofitClient == null) {

            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }

        return retrofitClient
    }
}