package com.example.stockpricetracker

import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.text.toDouble

fun extractDailyStockEntries(symbol: String,timeSeries: Map<String, Map<String, String>>): List<StockEntryDisplay> {

    return timeSeries
        .toSortedMap(compareByDescending { it })
        .entries
        .take(10)
        .map { (date, data) ->
            StockEntryDisplay(
                symbol = symbol,
                date,
                data["1. open"]!!.toDouble(),
                data["2. high"]!!.toDouble(),
                data["3. low"]!!.toDouble(),
                data["4. close"]!!.toDouble(),
                data["5. volume"]!!.toDouble()
            )
        }
}

fun extractWeeklyStockEntries(symbol: String,timeSeries: Map<String, Map<String, String>>): List<StockEntryDisplay> {

    return timeSeries
        .toSortedMap(compareByDescending { it })
        .entries
        .take(10)
        .map { (date, data) ->
            StockEntryDisplay(
                symbol = symbol,
                date,
                data["1. open"]!!.toDouble(),
                data["2. high"]!!.toDouble(),
                data["3. low"]!!.toDouble(),
                data["4. close"]!!.toDouble(),
                data["5. volume"]!!.toDouble()
            )
        }
}

fun extractMonthlyStockEntries(symbol: String, timeSeries: Map<String, Map<String, String>>): List<StockEntryDisplay> {
    val seenMonths = mutableSetOf<String>()
    val entries = mutableListOf<StockEntryDisplay>()

    for ((date, data) in timeSeries.toSortedMap(compareByDescending { it })) {
        val month = date.substring(0, 7)
        if (seenMonths.add(month)) {
            entries.add(
                StockEntryDisplay(
                    symbol = symbol, // ✅ Add this
                    date = date,
                    open = data["1. open"]!!.toDouble(),
                    high = data["2. high"]!!.toDouble(),
                    low = data["3. low"]!!.toDouble(),
                    close = data["4. close"]!!.toDouble(),
                    volume = data["5. volume"]!!.toDouble()
                )
            )
            if (entries.size == 10) break
        }
    }

    return entries
}

