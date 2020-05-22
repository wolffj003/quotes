package com.example.quotes.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.R
import kotlinx.android.synthetic.main.item_quote.view.*

class QuoteAdapter(private val quotes: ArrayList<Quote>, private val clickListener: (Quote) -> Unit) :
    RecyclerView.Adapter<QuoteAdapter.ViewHolderCard>() {

    class ViewHolderCard(cardViewText: View) : RecyclerView.ViewHolder(cardViewText) {
        fun bind(quote: Quote, clickListener: (Quote) -> Unit) {
            itemView.tvQuote.text = quote.quote
            itemView.tvQuotedEntity.text = quote.quotedEntity
            itemView.tvQuoteDate.text = quote.dateText  // Date van bouwen (net als in gamebacklog)
            itemView.setOnClickListener { clickListener(quote) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderCard {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false)
        return ViewHolderCard(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolderCard, position: Int) {
        holder.bind(quotes[position], clickListener)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }
}
