package com.max77.freedomfinanceeval.datasource.stockinfo

import com.max77.freedomfinanceeval.datasource.stockinfo.network.dto.StockInfo
import com.max77.freedomfinanceeval.datasource.stocknames.StockName
import kotlinx.coroutines.flow.Flow

interface StockInfoDataSource {
    fun getStockPricesFlow(stockList: List<StockName>): Flow<StockInfo>
}