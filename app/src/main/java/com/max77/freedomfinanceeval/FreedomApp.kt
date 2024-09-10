package com.max77.freedomfinanceeval

import android.app.Application
import com.max77.freedomfinanceeval.datasource.di.dataSourcesModule
import com.max77.freedomfinanceeval.repository.di.repoModule
import com.max77.freedomfinanceeval.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FreedomApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FreedomApp)
            modules(dataSourcesModule, repoModule, uiModule)
        }
    }
}