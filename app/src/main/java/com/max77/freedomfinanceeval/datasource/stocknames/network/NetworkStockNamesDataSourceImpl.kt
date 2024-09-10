package com.max77.freedomfinanceeval.datasource.stocknames.network

import com.max77.freedomfinanceeval.datasource.stocknames.StockName
import com.max77.freedomfinanceeval.datasource.stocknames.StockNamesDataSource
import com.max77.freedomfinanceeval.datasource.stocknames.network.ApiUtils.deserializeFromString
import com.max77.freedomfinanceeval.datasource.stocknames.network.dto.StockListRequest
import com.max77.freedomfinanceeval.datasource.stocknames.network.dto.StockListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NetworkStockNamesDataSourceImpl(
    private val ktorClient: HttpClient,
    private val json: Json
) : StockNamesDataSource {

    override val stockNamesFlow: Flow<List<StockName>> = flow {
        val body = StockListRequest(
            command = "getTopSecurities",
            StockListRequest.Params(
                objType = "stocks",
                exchange = "russia",
                gainers = 0,
                limit = 30
            )
        ).let { json.encodeToString(it) }

        // todo: вынести в сетевой слой
        val response = ktorClient.get(API_URL) {
            contentType(ContentType.Application.Json)
            parameter("q", body)
        }

        if (response.status.isSuccess()) {
            emit(json.deserializeFromString<StockListResponse>(response.bodyAsText()).stockList)
        } else {
            throw IllegalStateException(response.status.description)
        }
    }

    private companion object {
        const val API_URL = "https://tradernet.com/api/"
    }
}