package com.max77.freedomfinanceeval.repository.prices.network.dto


import com.max77.freedomfinanceeval.repository.prices.network.equalsToString
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * Brief stock info DTO
 *
 * JSON sample:
 * [
 *   "q",
 *   {
 *     "c": "TATNP",
 *     "chg": 4.1,
 *     ...
 *   }
 * ]
 */

@Serializable
data class StockPriceInfo(
    @SerialName("c")
    val ticker: String? = null,
    @SerialName("pcp")
    val priceChangePercent: Double? = null,
    @SerialName("ltr")
    val exchangeName: String? = null,
    @SerialName("name")
    val stockName: String? = null,
    @SerialName("ltp")
    val lastTradePrice: Double? = null,
    @SerialName("chg")
    val priceChangePoints: Double? = null,
    @SerialName("min_step")
    val minStep: Double? = null,
    @SerialName("ltt")
    val lastTradeTime: LocalDateTime? = null,
    @SerialName("ltc")
    val priceChangeDirection: String? = null
) {
    companion object {
        fun fromString(json: Json, s: String): StockPriceInfo =
            json.decodeFromString<JsonArray>(s).let { rootJson ->
                if (rootJson[0].equalsToString("q")) {
                    json.decodeFromJsonElement<StockPriceInfo>(rootJson[1])
                } else {
                    throw IllegalArgumentException("Incorrect Stock Info Format")
                }
            }
    }
}