package com.example.quotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_qod.*

class QoDActivity : AppCompatActivity() {

    private lateinit var viewModel: QoDViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qod)
        val actionbar = supportActionBar  // Actionbar customizations
        actionbar!!.title = "Quote of the Day"
        actionbar.setDisplayHomeAsUpEnabled(true)

        initViewModel()
        initViews()
    }

    private fun initViews() {
        viewModel.getQoD()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(QoDViewModel::class.java)

        viewModel.qod.observe(this, Observer { qod ->
            val quotes = qod.contents.quotes

            for (quote in quotes) {
                tvQoDQuote.text = quote.quote
                tvQoDQuotedEntity.text = quote.author
            }
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
