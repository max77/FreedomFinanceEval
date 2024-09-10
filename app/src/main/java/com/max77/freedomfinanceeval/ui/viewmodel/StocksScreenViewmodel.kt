package com.max77.freedomfinanceeval.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max77.freedomfinanceeval.repository.StockPricesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StocksScreenViewmodel(
    private val stockPricesRepository: StockPricesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<StockListItemInfo>>>(UiState.Loading())
    val uiState = _uiState.asStateFlow()
    private var updatesJob: Job? = null

    fun startUpdates() {
        updatesJob = viewModelScope.launch {
            launch {
                stockPricesRepository.stockPriceInfoFlow
                    .collect { priceInfo ->
                        _uiState.emit(
                            UiState.Data(
                                priceInfo
                                    .map { StockListItemInfo.fromStockPriceInfo(it) }
                                    .sortedBy { it.tickerName }
                            )
                        )
                    }
            }

            launch {
                stockPricesRepository.errorFlow
                    .collect {
                        _uiState.emit(UiState.Error(it.message ?: "General WTF Error"))
                        Log.e(LOG_TAG, "Error: ${it.message}")
                    }
            }
        }
    }

    fun stopUpdates() {
        updatesJob?.cancel()
        updatesJob = null
    }

    fun resetStocks() {
        viewModelScope.launch {
            stockPricesRepository.reloadStockNames()
        }
    }

    override fun onCleared() {
        stopUpdates()
        super.onCleared()
    }

    private companion object {
        val LOG_TAG: String = StocksScreenViewmodel::class.java.name
    }
}