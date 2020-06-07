package com.example.quotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.model.DOWNVOTE
import com.example.quotes.model.Quote
import com.example.quotes.model.QuoteAdapter
import com.example.quotes.model.UPVOTE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val ADD_QUOTE_REQUEST_CODE = 100
const val EXTRA_VIEW_QUOTE = "EXTRA_VIEW_QUOTE"

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var firestoreDb: FirebaseDatabase

    private var quotes = arrayListOf<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initViewModel()
        initFirebase()
    }

    private fun initFirebase() {
        firestoreDb = Firebase.database
        val dbReference = firestoreDb.getReference("quotes")

        // Read from the database & update local db
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (singleSnapshot in dataSnapshot.children) {
                    val fsQuote = singleSnapshot.getValue<Quote>()  // Fs object
                    if (fsQuote != null) {
                        mainActivityViewModel.syncRoomDb(fsQuote)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firestore", "Failed to read value.", error.toException())
            }
        })
    }

    private fun initViews() {
        fabAddQuote.setOnClickListener { onAddQuoteClick() }
        fabQoD.setOnClickListener { onQoDClick() }
        fabTopRated.setOnClickListener { onTopRatedClick() }

        viewManager = LinearLayoutManager(this)
        viewAdapter = QuoteAdapter(quotes) { quote : Quote, voteDirection: String -> quoteClicked(quote, voteDirection)}

        recyclerView = findViewById<RecyclerView>(R.id.rvQuotes).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fabQoD.visibility == View.VISIBLE) {
                    fabQoD.hide()
                    fabTopRated.hide()
                    fabAddQuote.hide()
                } else if (dy < 0 && fabQoD.visibility != View.VISIBLE) {
                    fabQoD.show()
                    fabTopRated.show()
                    fabAddQuote.show()
                }
            }
        })

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
        return when (item.itemId) {
            R.id.menuDelete -> {
                deleteQuotes()
                true
            }
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

    private fun onQoDClick() {
        val qoDIntent = Intent(this, QoDActivity::class.java)
        startActivity(qoDIntent)
    }

    private fun onTopRatedClick() {
        val topQuoteIntent = Intent(this, TopQuotesActivity::class.java)
        startActivity(topQuoteIntent)
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
