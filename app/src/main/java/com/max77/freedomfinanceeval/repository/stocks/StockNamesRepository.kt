package com.max77.freedomfinanceeval.repository.stocks

import kotlinx.coroutines.flow.Flow

typealias StockName = String

interface StockNamesRepository {
    val stockNamesFlow: Flow<List<StockName>>
}