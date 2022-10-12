package com.example.cryptoone.viewModels.coinDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoone.repository.CryptoRepository

class CoinDetailViewModelFactory(
    private val repository: CryptoRepository,
    private val id: String,
    private val currency: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinDetailViewModel(repository, id = id, currency = currency) as T
    }
}