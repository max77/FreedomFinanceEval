package com.max77.freedomfinanceeval.ui.viewmodel

import com.max77.freedomfinanceeval.repository.dto.StockPriceInfo
import kotlin.math.round

enum class StockPriceChangeDirection {
    Up, Down, Zero
}

data class StockListItemInfo(
    val tickerName: String,
    val priceChangePercent: Double? = null,
    val exchangeName: String? = null,
    val stockName: String? = null,
    val lastTradePrice: Double? = null,
    val priceChangePoints: Double? = null,
    val stockPriceChangeDirection: StockPriceChangeDirection? = null,
    val tickerIconUrl: String? = null,
    val numDigits: Int = 0,
) {
    companion object {
        fun fromStockPriceInfo(stockPriceInfo: StockPriceInfo) =
            StockListItemInfo(
                tickerName = stockPriceInfo.ticker ?: "",
                priceChangePercent = stockPriceInfo.priceChangePercent,
                exchangeName = stockPriceInfo.exchangeName,
                stockName = stockPriceInfo.stockName,
                lastTradePrice = stockPriceInfo.lastTradePrice?.roundTo(
                    stockPriceInfo.minStep ?: 0.0
                ),
                stockPriceChangeDirection = when {
                    (stockPriceInfo.lastPriceChange ?: 0.0) > 0 -> StockPriceChangeDirection.Up
                    (stockPriceInfo.lastPriceChange ?: 0.0) < 0 -> StockPriceChangeDirection.Down
                    else -> StockPriceChangeDirection.Zero
                },
                priceChangePoints = stockPriceInfo.priceChangePoints?.roundTo(
                    stockPriceInfo.minStep ?: 0.0
                ),
                tickerIconUrl = stockPriceInfo.ticker?.let { "https://tradernet.com/logos/get-logo-by-ticker?ticker=${it.lowercase()}" },
                numDigits = stockPriceInfo.minStep?.toBigDecimal()?.scale() ?: 0
            )

        private fun Double.roundTo(x: Double) = if (x > 0) round(this / x) * x else this
    }
}