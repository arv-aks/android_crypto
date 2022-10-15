package com.example.cryptoone.api

import com.example.cryptoone.model.details.CoinDetail
import com.example.cryptoone.model.market.MarketModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CryptoApi {

    @GET("/api/v3/coins/markets")
    suspend fun getMarkets(@Query("vs_currency") vs_currency: String): Response<List<MarketModel>>


    @GET("/api/v3/coins/markets")
    fun getCoinDetails(
        @Query("vs_currency") vs_currency: String,
        @Query("ids") id: String,
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = true,


    ): Call<CoinDetail>
}