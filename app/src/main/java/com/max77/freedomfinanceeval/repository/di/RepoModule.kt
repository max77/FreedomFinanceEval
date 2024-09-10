package com.max77.freedomfinanceeval.repository.di

import com.max77.freedomfinanceeval.datasource.stockinfo.StockInfoDataSource
import com.max77.freedomfinanceeval.datasource.stockinfo.network.NetworkStockInfoDataSourceImpl
import com.max77.freedomfinanceeval.datasource.stocknames.StockNamesDataSource
import com.max77.freedomfinanceeval.datasource.stocknames.network.NetworkStockNamesDataSourceImpl
import org.koin.dsl.module

val repoModule = module {
    single<StockNamesDataSource> { NetworkStockNamesDataSourceImpl(get(), get()) }
    single<StockInfoDataSource> { NetworkStockInfoDataSourceImpl(get(), get()) }
}