package com.max77.freedomfinanceeval.repository.prices.network

import android.util.Log
import com.max77.freedomfinanceeval.repository.prices.StockPricesRepository
import com.max77.freedomfinanceeval.repository.prices.network.dto.StockPriceInfo
import com.max77.freedomfinanceeval.repository.stocks.StockName
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.ws
import io.ktor.client.request.parameter
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive

class NetworkStockPricesRepositoryImpl(
    private val ktorClient: HttpClient,
    private val json: Json,
) : StockPricesRepository {
    override fun getStockPricesFlow(stockList: List<StockName>) = flow {
        ktorClient.ws(
            urlString = WS_ENDPOINT,
            request = { parameter("SID", SID) }
        ) {
            sendSerialized(makeStockPricesRequest(stockList))
            incoming
                .consumeAsFlow()
                .collect { frame ->
                    try {
                        (frame as? Frame.Text)?.readText()?.let {
                            Log.i(LOG_TAG, it)
                            emit(StockPriceInfo.fromString(json, it))
                        }
                    } catch (e: Exception) {
                        Log.e(LOG_TAG, "$e")
                    }
                }
        }
    }

    private fun makeStockPricesRequest(stockList: List<StockName>) =
        JsonArray(
            listOf(
                JsonPrimitive("quotes"),
                JsonArray(stockList.map { JsonPrimitive(it) })
            )
        )

    private companion object {
        val LOG_TAG: String = NetworkStockPricesRepositoryImpl::class.java.name
        const val SID = "03141a009b4f3a9d0f40969cf22d17cf"
        const val WS_ENDPOINT = "wss://wss.tradernet.com"
    }
}