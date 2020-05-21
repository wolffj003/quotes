package com.example.quotes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quotes.model.Quote

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quoteTable")
    fun getQuotes(): LiveData<List<Quote>>

    @Query("DELETE FROM quoteTable")
    fun deleteQuotes()

    @Insert
    suspend fun insertQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)
}
