package com.example.conexa

import android.app.Application
import com.example.conexa.di.apiModule
import com.example.conexa.di.dbModule
import com.example.conexa.di.productModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(apiModule, dbModule, productModule))
        }
    }
}