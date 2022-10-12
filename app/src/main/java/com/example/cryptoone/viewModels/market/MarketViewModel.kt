package com.example.cryptoone.viewModels.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoone.model.market.MarketsModel
import com.example.cryptoone.repository.CryptoRepository

//parameterized view model -> so define a factory
class MarketViewModel(private val repository: CryptoRepository) : ViewModel() {
    init {
        repository.getMarkets("usd")
    }
    fun getMarketRepository() : LiveData<MarketsModel>{
        return repository.markets
    }
}