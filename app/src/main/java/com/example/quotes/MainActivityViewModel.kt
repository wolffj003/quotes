package com.example.quotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.quotes.db.QuoteRepository
import com.example.quotes.model.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val quoteRepository = QuoteRepository(application.applicationContext)

    val quotes: LiveData<List<Quote>> = quoteRepository.getQuotes()

    fun insertQuote(quote: Quote) {
        ioScope.launch { quoteRepository.insertQuote(quote) }
    }

    fun deleteQuote(quote: Quote) {
        ioScope.launch { quoteRepository.deleteQuote(quote) }
    }

    fun deleteQuotes() {
        ioScope.launch { quoteRepository.deleteQuotes() }
    }
}