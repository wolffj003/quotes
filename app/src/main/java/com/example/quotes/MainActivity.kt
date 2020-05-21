package com.example.quotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.model.Quote
import com.example.quotes.model.QuoteAdapter
import kotlinx.android.synthetic.main.activity_main.*

const val ADD_QUOTE_REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainActivityViewModel: MainActivityViewModel

    private var quotes = arrayListOf<Quote>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fabAddQuote.setOnClickListener { onAddQuoteClick() }

        viewManager = LinearLayoutManager(this)
        viewAdapter = QuoteAdapter(quotes)

        recyclerView = findViewById<RecyclerView>(R.id.rvQuotes).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.quotes.observe(this, Observer { quotes ->
            this@MainActivity.quotes.clear()
            this@MainActivity.quotes.addAll(quotes)

            viewAdapter.notifyDataSetChanged()
        })
    }


    private fun onAddQuoteClick() {
        val intent = Intent(this, AddQuoteActivity::class.java)
        startActivityForResult(intent, ADD_QUOTE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_QUOTE_REQUEST_CODE -> {
                    val quote = data!!.getParcelableExtra<Quote>(EXTRA_QUOTE)
                    quote?.let { mainActivityViewModel.insertQuote(quote) }
                }
            }
        }
    }
}
