package com.example.quotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quotes.model.Quote


@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuotesDb : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        private const val DATABASE_NAME = "QUOTES_DATABASE"

        @Volatile
        private var INSTANCE: QuotesDb? = null

        fun getDatabase(context: Context): QuotesDb? {
            if (INSTANCE == null) {
                synchronized(QuotesDb::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            QuotesDb::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
