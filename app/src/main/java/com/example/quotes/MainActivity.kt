package com.example.quotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.model.Quote
import com.example.quotes.model.QuoteAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var quotes = arrayListOf<Quote>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        fabAddQuote.setOnClickListener {  }  // Voeg functie toe

        viewManager = LinearLayoutManager(this)  // Check of dit allemaal werkt.
        viewAdapter = QuoteAdapter(quotes)

        recyclerView = findViewById<RecyclerView>(R.id.rvQuotes).apply {
            setHasFixedSize(true)   // Performance tweak
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
