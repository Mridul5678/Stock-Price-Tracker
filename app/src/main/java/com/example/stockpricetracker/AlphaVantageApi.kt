package com.example.stockpricetracker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {
    @GET("query?function=TIME_SERIES_DAILY")
    fun getDailyStock(
        @Query("symbol") symbol: String,
        @Query("outputsize") outputSize: String,
        @Query("apikey") apiKey: String
    ): Call<AlphaVantageResponse>
}
