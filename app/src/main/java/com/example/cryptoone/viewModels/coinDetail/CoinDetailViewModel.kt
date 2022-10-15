package com.example.cryptoone.viewModels.coinDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoone.model.details.CoinDetail
import com.example.cryptoone.repository.CryptoRepository

//class CoinDetailViewModel(private val repository: CryptoRepository, id: String, currency: String) : ViewModel() {
//
//    init {
//        Log.e("view model: ", "id: $id == currency = $currency")
//        repository.getCoinDetails(id = id, vs_currency = currency)
//    }
//
//    fun getCoinDetailRepository() : LiveData<List<CoinDetail>>{
//        return repository.coinDetail
//    }
//
//
//}