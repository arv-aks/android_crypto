package com.example.cryptoone.viewModels.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoone.repository.CryptoRepository

//help to create view models
class MarketViewModelFactory(private val repository: CryptoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarketViewModel(repository) as T
    }
}