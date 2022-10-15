package com.example.cryptoone.viewModels.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoone.model.market.MarketModel
import com.example.cryptoone.repository.CryptoRepository
import com.example.cryptoone.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

//parameterized view model -> so define a factory
@HiltViewModel
class MarketViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    val marketResponseLiveData : LiveData<NetworkResult<List<MarketModel>>>
    get() = repository.markets

    init {
        getResponses()
    }

    private fun getResponses() {
        viewModelScope.launch {
            repository.getMarkets("usd")
        }
    }

}