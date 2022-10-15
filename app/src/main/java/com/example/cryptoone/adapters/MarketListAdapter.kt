package com.example.cryptoone.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoone.ChartActivity
import com.example.cryptoone.R
import com.example.cryptoone.model.market.MarketModel
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class MarketListAdapter(
    ctx: Context
) :
    RecyclerView.Adapter<MarketListAdapter.MyViewHolder>() {

    var marketList: List<MarketModel> = listOf()

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

        val percentage =  (marketList[position].priceChange24h * 100.0).roundToInt() / 100.0


        holder.name.text = marketList[position].name
        Picasso.get().load(marketList[position].image).into(holder.image)

        holder.price.text = "$ " + marketList[position].currentPrice
       if(percentage<0.0){
           //negative
           holder.priceChangePercentage.text = "$percentage %"
           holder.priceChangePercentage.setTextColor(Color.RED)
       }else if (percentage>0.0){
           //positive
           holder.priceChangePercentage.text = "+ $percentage %"
           holder.priceChangePercentage.setTextColor(Color.GREEN)
       }

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
    fun setMarketListItems(movieList: List<MarketModel>) {
        this.marketList = movieList;
        Log.w("Adapter: ", "${movieList[0]}")
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