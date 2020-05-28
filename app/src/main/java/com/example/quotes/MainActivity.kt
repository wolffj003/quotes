package com.example.quotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.model.DOWNVOTE
import com.example.quotes.model.Quote
import com.example.quotes.model.QuoteAdapter
import com.example.quotes.model.UPVOTE
import kotlinx.android.synthetic.main.activity_main.*

const val ADD_QUOTE_REQUEST_CODE = 100
const val EXTRA_VIEW_QUOTE = "EXTRA_VIEW_QUOTE"

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
        viewAdapter = QuoteAdapter(quotes) { quote : Quote, voteDirection: String -> quoteClicked(quote, voteDirection)}

        recyclerView = findViewById<RecyclerView>(R.id.rvQuotes).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        createItemTouchHelper().attachToRecyclerView(rvQuotes)
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.quotes.observe(this, Observer { quotes ->
            this@MainActivity.quotes.clear()
            this@MainActivity.quotes.addAll(quotes)

            viewAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val quoteToDelete = quotes[position]

                mainActivityViewModel.deleteQuote(quoteToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        deleteQuotes()

        return when (item.itemId) {
            R.id.menuDelete -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteQuotes() {
        mainActivityViewModel.deleteQuotes()
    }


    private fun quoteClicked(quote: Quote, voteDirection: String) {
        when (voteDirection) {
            "" -> {
                val viewQuoteIntent = Intent(this, ViewQuoteActivity::class.java)
                viewQuoteIntent.putExtra(EXTRA_VIEW_QUOTE, quote.id)

                startActivity(viewQuoteIntent)
            }
            UPVOTE -> {
                quote.score++
                mainActivityViewModel.updateQuote(quote)
            }
            DOWNVOTE -> {
                quote.score--
                mainActivityViewModel.updateQuote(quote)
            }
        }
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
