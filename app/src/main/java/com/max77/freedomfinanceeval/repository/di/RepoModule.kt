package com.max77.freedomfinanceeval.repository.di

import com.max77.freedomfinanceeval.repository.prices.StockPricesRepository
import com.max77.freedomfinanceeval.repository.prices.network.NetworkStockPricesRepositoryImpl
import com.max77.freedomfinanceeval.repository.stocks.StockNamesRepository
import com.max77.freedomfinanceeval.repository.stocks.memory.MemoryStockNamesRepositoryImpl
import com.max77.freedomfinanceeval.repository.stocks.network.NetworkStockNamesRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val repoModule = module {
    single<Json> { Json { ignoreUnknownKeys = true } }

    single<HttpClient> {
        HttpClient(OkHttp) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
                pingInterval = 10000
            }
        }
    }

    single<StockNamesRepository> { NetworkStockNamesRepositoryImpl(get(), get()) }
    single<StockPricesRepository> { NetworkStockPricesRepositoryImpl(get(), get()) }
}