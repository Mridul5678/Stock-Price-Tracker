package com.example.stockpricetracker

data class StockEntryDisplay(
    val symbol: String,
    val date: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double
)
