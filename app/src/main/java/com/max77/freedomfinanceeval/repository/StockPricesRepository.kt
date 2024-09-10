package com.max77.freedomfinanceeval.repository

import android.util.Log
import com.max77.freedomfinanceeval.datasource.stockinfo.StockInfoDataSource
import com.max77.freedomfinanceeval.datasource.stocknames.StockNamesDataSource
import com.max77.freedomfinanceeval.repository.dto.StockPriceInfo
import com.max77.freedomfinanceeval.repository.dto.updateFrom
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class StockPricesRepository(
    private val stockNamesDataSource: StockNamesDataSource,
    private val stockInfoDataSource: StockInfoDataSource,
    private val scope: CoroutineScope
) {
    private val currentStockPrices = mutableMapOf<String, StockPriceInfo?>().toMutableMap()

    private val _restartFlow = MutableStateFlow(0)
    private val _stockPricesFlow = flow {
        try {
            Log.i(LOG_TAG, "Stock prices repository flow started")

            if (currentStockPrices.isEmpty()) {
                Log.i(LOG_TAG, "Loading stock names...")
                stockNamesDataSource.stockNamesFlow.onEach { names ->
                    names.forEach { currentStockPrices[it] = null }
                }
            } else {
                Log.i(LOG_TAG, "Using cached stock names")
                listOf(currentStockPrices.keys.toList()).asFlow()
            }
                .flatMapLatest { stockInfoDataSource.getStockPricesFlow(it) }
                .filter { !it.ticker.isNullOrBlank() }
                .collect { stockInfo ->
                    currentStockPrices.compute(stockInfo.ticker!!) { _, item ->
                        StockPriceInfo.fromStockInfo(stockInfo).let {
                            item?.updateFrom(it) ?: it
                        }
                    }?.apply {
                        emit(
                            currentStockPrices.values
                                .filterNotNull()
                                .filter { isPriceInfoComplete(it) }
                                .toList()
                        )
                    }
                }
        } catch (e: CancellationException) {
            Log.i(LOG_TAG, "Stock prices repository flow stopped")
        }
    }

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    val stockPriceInfoFlow = _restartFlow
        .flatMapLatest { _stockPricesFlow }
        .catch { _errorFlow.emit(it) }
        .shareIn(scope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 3000), 1)

    fun reloadStockNames() {
        Log.i(LOG_TAG, "Reloading stock names...")

        currentStockPrices.clear()
        scope.launch {
            _restartFlow.emit(_restartFlow.value + 1)
        }
    }

    private fun isPriceInfoComplete(priceInfo: StockPriceInfo) =
        with(priceInfo) {
            !ticker.isNullOrBlank()
                    && !exchangeName.isNullOrBlank()
                    && priceChangePercent != null
                    && !stockName.isNullOrBlank()
                    && (lastTradePrice ?: 0.0) > 0.0
        }

    private companion object {
        val LOG_TAG: String = StockPricesRepository::class.java.name
    }
}