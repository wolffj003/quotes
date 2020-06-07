package com.example.quotes.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.R
import kotlinx.android.synthetic.main.item_top_quote.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TopQuoteAdapter(private val quotes: List<Quote>, private val clickListener: (Quote) -> Unit) :
    RecyclerView.Adapter<TopQuoteAdapter.ViewHolderCard>() {

    class ViewHolderCard(cardViewText: View) : RecyclerView.ViewHolder(cardViewText) {
        fun bind(quote: Quote, clickListener: (Quote) -> Unit) {
            itemView.tvTopQuote.text = quote.quote
            itemView.tvTopQuotedEntity.text = quote.quotedEntity
            itemView.tvTopQuoteDate.text = formatDate(quote.date)
            itemView.tvTopQuoteScore.text = quote.score.toString()

            itemView.setOnClickListener { clickListener(quote) }
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
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderCard {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top_quote, parent, false)
        return ViewHolderCard(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolderCard, position: Int) {
        holder.bind(quotes[position], clickListener)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }
}
