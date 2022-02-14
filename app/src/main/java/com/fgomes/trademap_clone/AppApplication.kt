package com.fgomes.trademap_clone

import android.app.Application
import com.fgomes.trademap_clone.di.daoModule
import com.fgomes.trademap_clone.di.repositoryModule
import com.fgomes.trademap_clone.di.serviceModule
import com.fgomes.trademap_clone.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(viewModelModule, serviceModule, repositoryModule, daoModule)
        }
    }
}