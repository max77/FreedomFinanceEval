package com.max77.freedomfinanceeval.repository.prices.network

import com.max77.freedomfinanceeval.repository.prices.StockPricesRepository
import com.max77.freedomfinanceeval.repository.prices.network.dto.StockPriceInfo
import com.max77.freedomfinanceeval.repository.stocks.StockName
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.parameter
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive

class NetworkStockPricesRepositoryImpl(
    private val ktorClient: HttpClient,
    private val json: Json,
) : StockPricesRepository {
    override fun getStockPricesFlow(stockList: List<StockName>) = flow {
        ktorClient.webSocket(
            urlString = WS_ENDPOINT,
            request = { parameter("SID", SID) }
        ) {
            sendSerialized(makeStockPricesRequest(stockList))
            while (true) {
                (incoming.receive() as? Frame.Text)?.readText()?.let {
                    try {
                        emit(StockPriceInfo.fromString(json, it))
                        Logger.ANDROID.log("WS: $it")
                    } catch (e: IllegalArgumentException) {
                        Logger.ANDROID.log("WS Error: $e")
                    }
                }
            }
        }
    }

    private fun makeStockPricesRequest(stockList: List<StockName>) =
        JsonArray(listOf(JsonPrimitive("quotes"), JsonArray(stockList.map { JsonPrimitive(it) })))

    private companion object {
        const val SID = "03141a009b4f3a9d0f40969cf22d17cf"
        const val WS_ENDPOINT = "wss://wss.tradernet.com"
    }
}