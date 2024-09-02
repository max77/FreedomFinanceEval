package com.max77.freedomfinanceeval.ui.viewmodel

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
)

fun StockListItemInfo.updateFrom(other: StockListItemInfo) =
    if (other.tickerName == tickerName) {
        copy(
            priceChangePercent = other.priceChangePercent ?: priceChangePercent,
            exchangeName = other.exchangeName ?: exchangeName,
            stockName = other.stockName ?: stockName,
            lastTradePrice = other.lastTradePrice ?: lastTradePrice,
            priceChangePoints = other.priceChangePoints ?: priceChangePoints,
            stockPriceChangeDirection = if (other.priceChangePercent == null) {
                stockPriceChangeDirection ?: StockPriceChangeDirection.Zero
            } else {
                val dp = other.priceChangePercent - (priceChangePercent ?: 0.0)
                if (dp > 0) {
                    StockPriceChangeDirection.Up
                } else if (dp < 0) {
                    StockPriceChangeDirection.Down
                } else {
                    StockPriceChangeDirection.Zero
                }
            },
            tickerIconUrl = other.tickerIconUrl ?: tickerIconUrl
        )
    } else {
        null
    }

