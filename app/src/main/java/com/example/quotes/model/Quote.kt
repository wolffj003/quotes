package com.example.quotes.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "quoteTable")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "quote")
    var quote: String,
    @ColumnInfo(name = "quotedEntity")
    val quotedEntity: String,
    @ColumnInfo(name = "date")
    val dateText: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "score")
    var score: Int
) : Parcelable