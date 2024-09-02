package com.max77.freedomfinanceeval.repository.stocks.network.dto

import com.max77.freedomfinanceeval.repository.stocks.StockName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StockListResponse(
    @SerialName("tickers")
    val stockList: List<StockName>
)