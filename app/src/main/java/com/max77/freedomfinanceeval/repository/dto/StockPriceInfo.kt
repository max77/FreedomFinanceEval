package com.max77.freedomfinanceeval.repository.dto


import com.max77.freedomfinanceeval.datasource.stockinfo.network.dto.StockInfo
import kotlinx.datetime.LocalDateTime

data class StockPriceInfo(
    val ticker: String? = null,
    val priceChangePercent: Double? = null,
    val exchangeName: String? = null,
    val stockName: String? = null,
    val lastTradePrice: Double? = null,
    val priceChangePoints: Double? = null,
    val minStep: Double? = null,
    val lastTradeTime: LocalDateTime? = null,
    val lastPriceChange: Double? = null,
) {
    companion object {
        fun fromStockInfo(stockInfo: StockInfo) = StockPriceInfo(
            ticker = stockInfo.ticker,
            priceChangePercent = stockInfo.priceChangePercent,
            exchangeName = stockInfo.exchangeName,
            stockName = stockInfo.stockName,
            lastTradePrice = stockInfo.lastTradePrice,
            priceChangePoints = stockInfo.priceChangePoints,
            minStep = stockInfo.minStep,
            lastTradeTime = stockInfo.lastTradeTime
        )
    }
}

fun StockPriceInfo.updateFrom(other: StockPriceInfo) =
    if (other.ticker == ticker) {
        copy(
            priceChangePercent = other.priceChangePercent ?: priceChangePercent,
            exchangeName = other.exchangeName ?: exchangeName,
            stockName = other.stockName ?: stockName,
            lastTradePrice = other.lastTradePrice ?: lastTradePrice,
            priceChangePoints = other.priceChangePoints ?: priceChangePoints,
            minStep = other.minStep ?: minStep,
            lastTradeTime = other.lastTradeTime ?: lastTradeTime,
            lastPriceChange = other.lastTradePrice?.minus(lastTradePrice ?: 0.0) ?: lastPriceChange
        )
    } else {
        null
    }
