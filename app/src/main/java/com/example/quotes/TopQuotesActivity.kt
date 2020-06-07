package com.example.quotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.model.Quote
import com.example.quotes.model.TopQuoteAdapter

class TopQuotesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainActivityViewModel: MainActivityViewModel // Maybe eigen viemodel? Of geen viewmodel zelfs.

    var sortedQuotes = mutableListOf<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_quotes)
        val actionbar = supportActionBar  // Actionbar customizations
        actionbar!!.title = "Top Quotes"
        actionbar.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = TopQuoteAdapter(sortedQuotes) { quote : Quote -> quoteClicked(quote)}

        recyclerView = findViewById<RecyclerView>(R.id.rvTopQuotes).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.quotes.observe(this, Observer { quotes ->
            var sortedQuotes = quotes.sortedWith(compareBy { it.score })
            sortedQuotes = sortedQuotes.reversed()

            this@TopQuotesActivity.sortedQuotes.clear()
            this@TopQuotesActivity.sortedQuotes.addAll(sortedQuotes)

            viewAdapter.notifyDataSetChanged()
        })
    }

    private fun quoteClicked(quote: Quote) {
        val viewQuoteIntent = Intent(this, ViewQuoteActivity::class.java)
        viewQuoteIntent.putExtra(EXTRA_VIEW_QUOTE, quote.id)

        startActivity(viewQuoteIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
