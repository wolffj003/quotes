package com.example.quotes.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QoDApi {
    companion object {
//        private const val baseUrl = "http://quotes.rest/"
        private const val baseUrl = "http://my-json-server.typicode.com/"  // Mocking API

        /**
         * @return [QoDService] The service class off the retrofit client.
         */
        fun createApi(): QoDService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val qodApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return qodApi.create(QoDService::class.java)
        }
    }
}
