package com.example.quotes.model.rest

import com.google.gson.annotations.SerializedName

data class QuoteResponse (

    @SerializedName("success") val success : Success,
    @SerializedName("contents") val contents : Contents,
    @SerializedName("baseurl") val baseurl : String,
    @SerializedName("copyright") val copyright : Copyright
)

data class Success (

    @SerializedName("total") val total : Int
)

data class Contents (

    @SerializedName("quotes") val quotes : List<Quotes>
)

data class Quotes (

    @SerializedName("quote") val quote : String,
    @SerializedName("length") val length : Int,
    @SerializedName("author") val author : String,
    @SerializedName("tags") val tags : List<String>,
    @SerializedName("category") val category : String,
    @SerializedName("language") val language : String,
    @SerializedName("date") val date : String,
    @SerializedName("permalink") val permalink : String,
    @SerializedName("id") val id : String,
    @SerializedName("background") val background : String,
    @SerializedName("title") val title : String
)

data class Copyright (

    @SerializedName("year") val year : Int,
    @SerializedName("url") val url : String
)
