package com.katana.koin.di

import com.google.gson.GsonBuilder
import com.katana.koin.data.AppDataManager
import com.katana.koin.data.DataManager
import com.katana.koin.data.local.prefs.AppPrefsHelper
import com.katana.koin.data.local.prefs.PrefsHelper
import com.katana.koin.data.remote.ApiHelper
import com.katana.koin.data.remote.AppApiHelper
import com.katana.koin.ui.MainViewModel
import com.katana.koin.ui.other.OtherHihi
import com.utils.SchedulerProvider
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

//define app module gson, data manager, etc...
val appModule: Module = module {

    single { SchedulerProvider() }

    single { AppPrefsHelper(get(), "Katana", get()) as PrefsHelper }

    single { AppApiHelper() as ApiHelper }

    single { AppDataManager(get(), get()) as DataManager }

    single { GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()!! }

    factory { OtherHihi(get(), get()) }
}

//define list view model
val viewModule = module {
    viewModel { MainViewModel(get(), get()) }
}

val otherModule = module {
    //    provide { OtherHihi() }
}

val mvvmModule = listOf(appModule, viewModule)