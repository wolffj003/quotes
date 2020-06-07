package com.example.quotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.quotes.db.QuoteRepository
import com.example.quotes.model.Quote

class ViewQuoteViewModel(application: Application): AndroidViewModel(application) {
    private val quoteRepository = QuoteRepository(application.applicationContext)

    fun getQuote(id: Long): LiveData<Quote> {
        return quoteRepository.getQuote(id)
    }
}