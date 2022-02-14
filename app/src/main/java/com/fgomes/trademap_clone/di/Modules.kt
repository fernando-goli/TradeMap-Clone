package com.fgomes.trademap_clone.di

import com.fgomes.trademap_clone.ui.MainViewModel
import com.fgomes.trademap_clone.data.AppDatabase
import com.fgomes.trademap_clone.data.RetrofitService
import com.fgomes.trademap_clone.repository.AcaoRepository
import com.fgomes.trademap_clone.ui.AcaoViewModel
import com.fgomes.trademap_clone.ui.Login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { LoginViewModel(get() ) }
    viewModel { MainViewModel(get() ) }
    viewModel { AcaoViewModel(get()) }
}

val serviceModule = module {
    single { RetrofitService.createService() }
}

val repositoryModule = module {
    single { AcaoRepository(get(), get()) }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).acaoDao() }
}