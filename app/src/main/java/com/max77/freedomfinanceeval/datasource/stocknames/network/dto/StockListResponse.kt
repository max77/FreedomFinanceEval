package com.max77.freedomfinanceeval.datasource.stocknames.network.dto

import com.max77.freedomfinanceeval.datasource.stocknames.StockName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StockListResponse(
    @SerialName("tickers")
    val stockList: List<StockName>
)