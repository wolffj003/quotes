package com.example.quotes.rest

import com.example.quotes.model.rest.QuoteResponse
import retrofit2.Call
import retrofit2.http.GET

interface QoDService {
//    @GET("/qod")
    @GET("/wolffj003/apimocker/quotes")  // Mocking API
    fun getQoD(): Call<QuoteResponse>
}
