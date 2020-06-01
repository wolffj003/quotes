package com.example.quotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.model.Quote
import kotlinx.android.synthetic.main.activity_view_quote.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ViewQuoteActivity : AppCompatActivity() {

    private lateinit var viewQuoteViewModel: ViewQuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_quote)
        val actionbar = supportActionBar  // Actionbar customizations
        actionbar!!.title = "View Quote"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val quoteId = intent.extras?.getLong(EXTRA_VIEW_QUOTE)
        initViews(quoteId)
    }

    private fun initViews(quoteId: Long?) {
        quoteId?.let {
            viewQuoteViewModel = ViewModelProvider(this).get(ViewQuoteViewModel::class.java)

            val quote: LiveData<Quote> = viewQuoteViewModel.getQuote(quoteId)
            quote.observe(this, Observer { observedQuote ->



                this@ViewQuoteActivity.tvViewQuote.text = observedQuote.quote
                this@ViewQuoteActivity.tvViewQuotedEntity.text = observedQuote.quotedEntity
                this@ViewQuoteActivity.tvViewQuoteDate.text = formatDate(observedQuote.date)
                this@ViewQuoteActivity.tvViewQuoteDescription.text = observedQuote.description
                this@ViewQuoteActivity.tvViewQuoteScore.text = getString(R.string.tvViewQuoteScore, observedQuote.score)
            })
        }
    }

    private fun formatDate(date: Date): String? {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)

        formatter.format(date)
        var fmtDate: String? = null
        try {
            fmtDate = formatter.format(date)
        } catch (e: ParseException) {
            return fmtDate
        }
        return fmtDate
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
