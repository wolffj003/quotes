package com.example.quotes.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.quotes.model.Quote

class QuoteRepository(context: Context) {

    private val quoteDao: QuoteDao

    init {
        val database = QuotesDb.getDatabase(context)
        quoteDao = database!!.quoteDao()
    }

    fun getQuotes(): LiveData<List<Quote>> {
        return quoteDao.getQuotes()
    }

    fun deleteQuotes() {
        return quoteDao.deleteQuotes()
    }

    suspend fun insertQuote(quote: Quote) {
        quoteDao.insertQuote(quote)
    }

    suspend fun deleteQuote(quote: Quote) {
        quoteDao.deleteQuote(quote)
    }
}
