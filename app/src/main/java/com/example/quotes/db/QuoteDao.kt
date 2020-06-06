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

    @Query("SELECT * FROM quoteTable WHERE id=:id")
    fun getQuote(id: Long): LiveData<Quote>

    @Query("SELECT * FROM quoteTable WHERE uuid=:uuid")
    fun getQuoteByUuid(uuid: String): Quote

    @Insert
    suspend fun insertQuote(quote: Quote)

    @Update
    suspend fun updateQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)
}
