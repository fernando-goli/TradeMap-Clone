package com.fgomes.trademap_clone.di

import com.fgomes.trademap_clone.data.AppDatabase
import com.fgomes.trademap_clone.data.RetrofitService
import com.fgomes.trademap_clone.domain.ApiService
import com.fgomes.trademap_clone.repository.LoginRepository
import com.fgomes.trademap_clone.ui.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel{ LoginViewModel(get() ) }
}

val serviceModule = module {
    single { RetrofitService.createService() }
}

val repositoryModule = module {
    single { LoginRepository(get()) }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).acaoDao() }
}