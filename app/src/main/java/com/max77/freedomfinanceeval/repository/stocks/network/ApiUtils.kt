package com.max77.freedomfinanceeval.repository.stocks.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class StocksApiException(
    @SerialName("code")
    val errorCode: Int,

    @SerialName("error")
    val error: String?,

    @SerialName("errMsg")
    val errorMessage: String?
) : Exception() {
    override val message: String?
        get() = errorMessage
}

object ApiUtils {
    inline fun <reified T> Json.deserializeFromString(json: String): T {
        return try {
            val stocksException = decodeFromString<StocksApiException>(json)
            throw stocksException
        } catch (e: IllegalArgumentException) {
            decodeFromString<T>(json)
        }
    }
}