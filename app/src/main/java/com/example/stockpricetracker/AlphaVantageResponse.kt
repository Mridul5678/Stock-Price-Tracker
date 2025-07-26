package com.example.stockpricetracker

import com.google.gson.annotations.SerializedName

data class AlphaVantageResponse(
    @SerializedName("Meta Data")
    val metaData: Map<String, String>,

    @SerializedName("Time Series (Daily)")
    val timeSeries: Map<String, Map<String, String>>
)
