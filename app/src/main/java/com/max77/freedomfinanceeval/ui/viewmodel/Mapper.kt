package com.max77.freedomfinanceeval.ui.viewmodel

import com.max77.freedomfinanceeval.repository.prices.network.dto.StockPriceInfo

fun StockPriceInfo.toStockListItemInfo() =
    StockListItemInfo(
        tickerName = ticker ?: "",
        priceChangePercent = priceChangePercent,
        exchangeName = exchangeName,
        stockName = stockName,
        lastTradePrice = lastTradePrice,
        stockPriceChangeDirection = when (priceChangeDirection) {
            "U" -> StockPriceChangeDirection.Up
            "D" -> StockPriceChangeDirection.Down
            else -> StockPriceChangeDirection.Zero
        },
        priceChangePoints = priceChangePoints,
        tickerIconUrl = ticker?.let { "https://tradernet.com/logos/get-logo-by-ticker?ticker=${it.lowercase()}" }
    )
