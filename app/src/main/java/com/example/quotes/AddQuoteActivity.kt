package com.example.quotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quotes.model.Quote
import kotlinx.android.synthetic.main.activity_add_quote.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
        val tiletQuoteDescriptionText = tiletQuoteDescription.text.toString()
        val tiletQuoteDateDayText = tiletQuoteDateDay.text.toString()
        val tiletQuoteDateMonthText = tiletQuoteDateMonth.text.toString()
        val tiletQuoteDateYearText = tiletQuoteDateYear.text.toString()


        if (
            tiletQuoteText.isNotBlank() and
            tiletQuotedEntityText.isNotBlank() and
            tiletQuoteDescriptionText.isNotBlank() and
            tiletQuoteDateDayText.isNotBlank() and
            tiletQuoteDateMonthText.isNotBlank() and
            tiletQuoteDateYearText.isNotBlank()
        ) {
            val date = strToDate("$tiletQuoteDateDayText-$tiletQuoteDateMonthText-$tiletQuoteDateYearText")

            date?.let {
                val quote = Quote(
                    quote = tiletQuoteText,
                    quotedEntity = tiletQuotedEntityText,
                    date = date,
                    description = tiletQuoteDescriptionText,
                    score = 0
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_QUOTE, quote)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                return
            }
            toastError(getString(R.string.enterValidDate))
            return
        } else toastError(getString(R.string.tstFillForms))
    }

    private fun strToDate(dateStr: String) : Date? {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        formatter.isLenient = false
        var date: Date? = null
        try {
            date = formatter.parse(dateStr)
        } catch (e: ParseException) {
            return date
        }

        return date
    }

    private fun toastError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
