package com.max77.freedomfinanceeval.ui.viewmodel

import com.max77.freedomfinanceeval.repository.prices.network.dto.StockPriceInfo
import kotlin.math.round

fun StockPriceInfo.toStockListItemInfo() =
    StockListItemInfo(
        tickerName = ticker ?: "",
        priceChangePercent = priceChangePercent,
        exchangeName = exchangeName,
        stockName = stockName,
        lastTradePrice = lastTradePrice?.roundTo(minStep ?: 0.0),
        stockPriceChangeDirection = when (priceChangeDirection) {
            "U" -> StockPriceChangeDirection.Up
            "D" -> StockPriceChangeDirection.Down
            else -> StockPriceChangeDirection.Zero
        },
        priceChangePoints = priceChangePoints?.roundTo(minStep ?: 0.0),
        tickerIconUrl = ticker?.let { "https://tradernet.com/logos/get-logo-by-ticker?ticker=${it.lowercase()}" },
        numDigits = minStep?.toBigDecimal()?.scale() ?: 0
    )

private fun Double.roundTo(x: Double) = if (x > 0) round(this / x) * x else this