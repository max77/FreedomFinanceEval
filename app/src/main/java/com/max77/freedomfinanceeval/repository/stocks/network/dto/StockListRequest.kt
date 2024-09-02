package com.max77.freedomfinanceeval.repository.stocks.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StockListRequest(
    @SerialName("cmd")
    val command: String,
    @SerialName("params")
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("type")
        val objType: String,
        @SerialName("exchange")
        val exchange: String,
        @SerialName("gainers")
        val gainers: Int,
        @SerialName("limit")
        val limit: Int
    )
}