package com.max77.freedomfinanceeval.ui.di

import com.max77.freedomfinanceeval.ui.viewmodel.StocksScreenViewmodel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { StocksScreenViewmodel(get(), get()) }
}