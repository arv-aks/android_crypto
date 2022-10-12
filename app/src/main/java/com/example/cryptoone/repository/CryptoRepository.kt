package com.example.cryptoone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptoone.api.CryptoApi
import com.example.cryptoone.model.details.CoinDetail
import com.example.cryptoone.model.details.Sparkline_in_7d
import com.example.cryptoone.model.market.MarketsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoRepository(private val cryptoApi: CryptoApi) {

    private val tag = "CRYPTO_REPO"

    private val marketLiveData = MutableLiveData<MarketsModel>()

    val markets : LiveData<MarketsModel>
    get() = marketLiveData

    private val coinLiveData = MutableLiveData<List<CoinDetail>>()

    val coinDetail: LiveData<List<CoinDetail>>
    get() = coinLiveData

    fun getMarkets(vs_currency: String){
        val result =cryptoApi.getMarkets(vs_currency)
        result.enqueue(object : Callback<MarketsModel> {
            override fun onResponse(call: Call<MarketsModel>, response: Response<MarketsModel>) {
              if(response.isSuccessful){
                  marketLiveData.postValue(response.body())
              }
            }
            override fun onFailure(call: Call<MarketsModel>, t: Throwable) {
                Log.e(tag, "Failure: $call")
            }

        });
    }

    fun getCoinDetails(id: String, vs_currency: String){
        val result = cryptoApi.getCoinDetails(id = id , vs_currency = vs_currency)

        result.enqueue(object : Callback<List<CoinDetail>>{

            override fun onResponse(
                call: Call<List<CoinDetail>>,
                response: Response<List<CoinDetail>>
            ) {
                coinLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<CoinDetail>>, t: Throwable) {
                Log.e(tag, "Failure: $call -> ${t.message}")
            }


        })
    }
}