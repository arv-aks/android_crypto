package com.example.cryptoone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptoone.api.CryptoApi
import com.example.cryptoone.model.details.CoinDetail
import com.example.cryptoone.model.market.MarketModel
import com.example.cryptoone.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val cryptoApi: CryptoApi) {

    private val tag = "CRYPTO_REPO"

    private val marketResponseLiveData = MutableLiveData<NetworkResult<List<MarketModel>>>()

    val markets: LiveData<NetworkResult<List<MarketModel>>>
        get() = marketResponseLiveData

    private val coinLiveData = MutableLiveData<List<CoinDetail>>()

    val coinDetail: LiveData<List<CoinDetail>>
        get() = coinLiveData

    suspend fun getMarkets(vs_currency: String) {

        marketResponseLiveData.postValue(NetworkResult.Loading())


        val response = cryptoApi.getMarkets(vs_currency)
        if (response.isSuccessful && response.body() != null) {
            marketResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            marketResponseLiveData.postValue(NetworkResult.Error("Error occurred!"))
        } else {
            marketResponseLiveData.postValue(NetworkResult.Error("Error occurred!"))
        }
    }


//   suspend fun getMarkets(vs_currency: String){
//        val result =cryptoApi.getMarkets(vs_currency)
//        result.enqueue(object : Callback<List<MarketModel>> {
//            override fun onResponse(call: Call<List<MarketModel>>, response: Response<List<MarketModel>>) {
//              if(response.isSuccessful){
//                  Log.w("repository: ", "${response.body()?.get(0)}")
//                  marketLiveData.postValue(response.body())
//              }
//            }
//            override fun onFailure(call: Call<List<MarketModel>>, t: Throwable) {
//                Log.e(tag, "Failure: $call")
//            }
//
//        });
//    }

//    fun getCoinDetails(id: String, vs_currency: String){
//        val result = cryptoApi.getCoinDetails(id = id , vs_currency = vs_currency)
//
//        result.enqueue(object : Callback<List<MarketModel>>{
//
//            override fun onResponse(
//                call: Call<List<MarketModel>>,
//                response: Response<List<MarketModel>>
//            ) {
//                coinLiveData.postValue(response.body())
//            }
//
//            override fun onFailure(call: Call<List<CoinDetail>>, t: Throwable) {
//                Log.e(tag, "Failure: $call -> ${t.message}")
//            }
//
//
//        })
//    }
}