package com.example.cryptoone.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoone.ChartActivity
import com.example.cryptoone.R
import com.example.cryptoone.model.market.MarketsModelItem
import com.squareup.picasso.Picasso


class MarketListAdapter(
    ctx: Context
) :
    RecyclerView.Adapter<MarketListAdapter.MyViewHolder>() {

    var marketList: List<MarketsModelItem> = listOf()


    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(ctx)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.market_item, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.name.text = marketList[position].name
        Picasso.get()
            .load(marketList[position].image)
            .into(holder.image);
        holder.price.text = "$ " + marketList[position].current_price
        holder.priceChangePercentage.text =
            marketList[position].price_change_percentage_24h.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ChartActivity::class.java).apply {
                putExtra("id", marketList[position].id)
            }

            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return marketList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMarketListItems(movieList: List<MarketsModelItem>) {
        this.marketList = movieList;
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView
        val image: ImageView
        val price: TextView
        val priceChangePercentage: TextView


        init {
            name = itemView.findViewById(R.id.txt_name)
            image = itemView.findViewById(R.id.img_view)
            price = itemView.findViewById(R.id.txt_price)
            priceChangePercentage = itemView.findViewById(R.id.txt_price_change)


        }

    }
}