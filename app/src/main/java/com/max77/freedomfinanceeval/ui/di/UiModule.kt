package com.max77.freedomfinanceeval.ui.di

import com.max77.freedomfinanceeval.repository.StockPricesRepository
import com.max77.freedomfinanceeval.ui.viewmodel.StocksScreenViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    single<StockPricesRepository> {
        StockPricesRepository(
            get(),
            get(),
            CoroutineScope(Dispatchers.IO)
        )
    }
    viewModel { StocksScreenViewmodel(get()) }
}