package com.example.quotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.quotes.model.Quote
import kotlinx.android.synthetic.main.activity_add_quote.*

const val EXTRA_QUOTE = "EXTRA_QUOTE"

class AddQuoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quote)
        val actionbar = supportActionBar  // Actionbar customizations
        actionbar!!.title = "Add Quote"
        actionbar.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    private fun initViews() { fabSaveQuote.setOnClickListener{ onSaveClick() } }  // Maak van deze een back button (net als google keep). Maybe not.

    private fun onSaveClick() {
        val tiletQuoteText: String = tiletQuote.text.toString()
        val tiletQuotedEntityText = tiletQuotedEntity.text.toString()
        val tiletQuoteDateText = tiletQuoteDate.text.toString()
        val tiletQuoteDescriptionText = tiletQuoteDescription.text.toString()

        if (
            tiletQuoteText.isNotBlank() and
            tiletQuotedEntityText.isNotBlank() and
            tiletQuoteDateText.isNotBlank() and
            tiletQuoteDescriptionText.isNotBlank()
        ) {
            val quote = Quote(
                quote = tiletQuoteText,
                quotedEntity = tiletQuotedEntityText,
                dateText = tiletQuoteDateText,  // Maak er een date van (net als gamebacklog)
                description = tiletQuoteDescriptionText
            )
            val resultIntent = Intent()

            resultIntent.putExtra(EXTRA_QUOTE, quote)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        } else Toast.makeText(this, R.string.tstFillForms, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
