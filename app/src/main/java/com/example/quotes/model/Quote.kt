package com.example.quotes.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import java.util.Date

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
    val date: Date,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "score")
    var score: Int
) : Parcelable

@IgnoreExtraProperties
data class QuoteTest(
    var id: Long = 0,
    var quote: String = "",
    val quotedEntity: String = "",
    val date: DateClass = DateClass(),
    val description: String = "",
    var score: Int = 0
)

@IgnoreExtraProperties
data class DateClass(
    val date: Int = 0,
    val day: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0,
    val month: Int = 0,
    val seconds: Int = 0,
    val time: Long = 0,
    val timezondeOffset: Int = 0,
    val year: Int = 0
)
