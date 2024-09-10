package com.max77.freedomfinanceeval.datasource.stocknames

import kotlinx.coroutines.flow.Flow

typealias StockName = String

interface StockNamesDataSource {
    val stockNamesFlow: Flow<List<StockName>>
}