package com.max77.freedomfinanceeval.repository.prices

import com.max77.freedomfinanceeval.repository.prices.network.dto.StockPriceInfo
import com.max77.freedomfinanceeval.repository.stocks.StockName
import kotlinx.coroutines.flow.Flow

interface StockPricesRepository {
    fun getStockPricesFlow(stockList: List<StockName>): Flow<StockPriceInfo>
}