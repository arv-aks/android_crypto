package com.example.cryptoone

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cryptoone.adapters.MarketListAdapter
import com.example.cryptoone.api.CryptoApi
import com.example.cryptoone.api.AppModule
import com.example.cryptoone.model.details.CoinDetail
import com.example.cryptoone.repository.CryptoRepository
import com.example.cryptoone.utils.NetworkResult
import com.example.cryptoone.viewModels.market.MarketViewModel
import com.example.cryptoone.viewModels.market.MarketViewModelFactory
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var marketRecyclerView: RecyclerView

    lateinit var marketListAdapter: MarketListAdapter

    lateinit var progress: ProgressBar

    private val marketViewModel by viewModels<MarketViewModel>()


/*

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if (id == R.id.icon1){
            val intent = Intent(applicationContext, ChartActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.circularProgress)

        marketRecyclerView = findViewById(R.id.recyclerView)

        marketListAdapter = MarketListAdapter(this)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        marketRecyclerView.adapter = marketListAdapter

        marketRecyclerView.layoutManager =layoutManager

        marketRecyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                layoutManager.orientation
            )
        )

        marketViewModel.marketResponseLiveData.observe(this) {
            progress.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    marketListAdapter.setMarketListItems(it.data!!)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(applicationContext, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progress.isVisible = true
                }
            }
        }



    }



   /* private fun test() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->

                var strResp = response.toString()
                Toast.makeText(applicationContext, "$strResp", Toast.LENGTH_SHORT).show()
//                val jsonObj: JSONObject = JSONObject(strResp)
//                val jsonArray: JSONArray = jsonObj.getJSONArray("items")
//                var str_user: String = ""
//                for (i in 0 until jsonArray.length()) {
//                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
//                    str_user = str_user + "\n" + jsonInner.get("login")
//                }
////                textView!!.text = "response : $str_user "
            },
            { Toast.makeText(applicationContext, "not working", Toast.LENGTH_SHORT).show() })
        queue.add(stringReq)
    }*/
}