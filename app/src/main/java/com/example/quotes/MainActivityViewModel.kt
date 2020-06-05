package com.example.quotes

import android.app.Application
import android.service.autofill.FillResponse
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.quotes.db.QuoteRepository
import com.example.quotes.model.Quote
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val quoteRepository = QuoteRepository(application.applicationContext)

    val quotes: LiveData<List<Quote>> = quoteRepository.getQuotes()

    val firestoreDb = Firebase.database


    fun insertQuote(quote: Quote) {
        ioScope.launch { quoteRepository.insertQuote(quote) }
        insertFirestore(quote)
    }

    fun deleteQuote(quote: Quote) {
        ioScope.launch { quoteRepository.deleteQuote(quote) }
    }

    fun updateQuote(quote: Quote) {
        ioScope.launch { quoteRepository.updateQuote(quote) }
    }

    fun deleteQuotes() {
        ioScope.launch { quoteRepository.deleteQuotes() }
    }

    fun getQuote(id: Long): LiveData<Quote> {
        return quoteRepository.getQuote(id)
    }

    private fun insertFirestore(quote: Quote) {
        val uuid = UUID.randomUUID().toString()
        val fsQuotesReference = firestoreDb.getReference("quotes")
        fsQuotesReference.child(uuid).setValue(quote)  // Bij ophalen quotes dealen met date
    }
}
