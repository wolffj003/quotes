package com.example.quotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quotes.db.QoDRepository
import com.example.quotes.model.rest.QuoteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QoDViewModel(application: Application) : AndroidViewModel(application) {

    private val qoDRepository = QoDRepository()
    val qod = MutableLiveData<QuoteResponse>()
    val error = MutableLiveData<String>()

    /**
     * Get a qod from the repository using Retrofit.
     * onResponse if the response is successful populate the [qod] object.
     * If the call encountered an error then populate the [error] object.
     */
    fun getQoD() {
        qoDRepository.getQoD().enqueue(object : Callback<QuoteResponse> {
            override fun onResponse(call: Call<QuoteResponse>, response: Response<QuoteResponse>) {
                if (response.isSuccessful) qod.value = response.body()
                else error.value = "An error occurred: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}
