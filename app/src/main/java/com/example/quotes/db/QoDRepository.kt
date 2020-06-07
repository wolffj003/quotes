package com.example.quotes.db

import com.example.quotes.rest.QoDApi
import com.example.quotes.rest.QoDService

class QoDRepository {
    private val qodApi: QoDService = QoDApi.createApi()

    fun getQoD() = qodApi.getQoD()
}
