package com.max77.freedomfinanceeval.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max77.freedomfinanceeval.repository.prices.StockPricesRepository
import com.max77.freedomfinanceeval.repository.prices.network.dto.StockPriceInfo
import com.max77.freedomfinanceeval.repository.stocks.StockNamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class StocksScreenViewmodel(
    private val stockNamesRepository: StockNamesRepository,
    private val pricesRepository: StockPricesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<StockListItemInfo>>>(UiState.Loading())
    val uiState = _uiState.asStateFlow()
    private var updatesJob: Job? = null

    private val currentStockInfos = sortedMapOf<String, StockListItemInfo>().toMutableMap()

    init {
        startUpdates()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startUpdates(reloadStockList: Boolean = false) {
        updatesJob = viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(UiState.Loading())

            val stockNames = when (val v = _uiState.value) {
                is UiState.Data -> v.data.map { it.tickerName }
                else -> null
            }.orEmpty()

            if (stockNames.isEmpty() || reloadStockList) {
                currentStockInfos.clear()
                stockNamesRepository.stockNamesFlow
            } else {
                listOf(stockNames).asFlow()
            }
                .flatMapLatest { pricesRepository.getStockPricesFlow(it) }
                .filter { !it.ticker.isNullOrBlank() }
                .catch { _uiState.emit(UiState.Error(it.message ?: "General Error")) }
                .collect { priceInfo ->
                    val prevListItem = currentStockInfos[priceInfo.ticker]
                    var emitFlag = false

                    if (prevListItem == null) {
                        if (isPriceInfoComplete(priceInfo)) {
                            currentStockInfos[priceInfo.ticker] = priceInfo.toStockListItemInfo()
                            emitFlag = true
                        }
                    } else {
                        currentStockInfos[priceInfo.ticker] =
                            prevListItem.updateFrom(priceInfo.toStockListItemInfo())
                        emitFlag = true
                    }

                    if (emitFlag) {
                        _uiState.emit(
                            UiState.Data(currentStockInfos.values.toList())
                        )
                    }
                }
        }
    }

    private fun isPriceInfoComplete(priceInfo: StockPriceInfo) =
        !priceInfo.ticker.isNullOrBlank()
                && !priceInfo.exchangeName.isNullOrBlank()
                && priceInfo.priceChangePercent != null
                && !priceInfo.stockName.isNullOrBlank()

    private fun stopUpdates() {
        updatesJob?.cancel()
        updatesJob = null
    }

    override fun onCleared() {
        stopUpdates()
        super.onCleared()
    }

}