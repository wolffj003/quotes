package com.example.quotes

import android.app.Application
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

    private val firestoreDb = Firebase.database
    private val fsQuotesReference = firestoreDb.getReference("quotes")

    fun insertQuote(quote: Quote) {
        quote.dateLong = quote.date.time

        ioScope.launch {
            quoteRepository.insertQuote(quote)
        }
        insertQuoteFirestore(quote, quote.uuid)
    }

    fun deleteQuote(quote: Quote) {
        ioScope.launch { quoteRepository.deleteQuote(quote) }
        deleteQuoteFirestore(quote.uuid)
    }

    fun updateQuote(quote: Quote) {
        quote.dateLong = quote.date.time

        ioScope.launch { quoteRepository.updateQuote(quote) }
        updateQuoteFirestore(quote.uuid, quote)
    }

    fun deleteQuotes() {
        ioScope.launch { quoteRepository.deleteQuotes() }
        deleteQuotesFirestore()
    }

    private fun getQuoteByUuid(uuid: String): Quote {
        return quoteRepository.getQuoteByUuid(uuid)
    }


    private fun insertQuoteFirestore(quote: Quote, uuid: String) {
        fsQuotesReference.child(uuid).setValue(quote)
    }

    private fun deleteQuoteFirestore(uuid: String) {
        fsQuotesReference.child(uuid).removeValue()
    }

    private fun deleteQuotesFirestore() {
        fsQuotesReference.removeValue()
    }

    private fun updateQuoteFirestore(uuid: String, quote: Quote) {
        fsQuotesReference.child(uuid).setValue(quote)
    }


    fun syncRoomDb(fsQuote: Quote) {
        ioScope.launch {
            val localQuote = getQuoteByUuid(fsQuote.uuid)
            fsQuote.id = localQuote.id
            fsQuote.date = Date(fsQuote.dateLong)

            quoteRepository.updateQuote(fsQuote)
        }
    }
}
