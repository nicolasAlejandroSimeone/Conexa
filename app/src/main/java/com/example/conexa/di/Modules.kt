package com.example.conexa.di

import com.example.conexa.repositories.ProductRepository
import com.example.conexa.repositories.local.AppDatabase
import com.example.conexa.repositories.remote.ApiInstance
import com.example.conexa.repositories.services.ProductService
import com.example.conexa.viewModel.CartViewModel
import com.example.conexa.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { ApiInstance.create() }
}

val dbModule = module {
    single{ AppDatabase.getDatabase(get()) }
    single{ (get<AppDatabase>().ProductDAO())}
}

val productModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { CartViewModel(get()) }
    single { ProductRepository(get(), get()) }
    single { ProductService((get())) }
}