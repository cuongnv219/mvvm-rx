package com.katana.koin.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.katana.koin.data.AppDataManager
import com.katana.koin.data.DataManager
import com.katana.koin.data.local.prefs.AppPrefsHelper
import com.katana.koin.data.local.prefs.PrefsHelper
import com.katana.koin.data.remote.ApiHelper
import com.katana.koin.data.remote.AppApiHelper
import com.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSchedulerProvider() = SchedulerProvider()

    @Singleton
    @Provides
    fun provideAppApiHelper(): ApiHelper = AppApiHelper()

    @Singleton
    @Provides
    fun provideAppPrefsHelper(@ApplicationContext context: Context, gson: Gson): PrefsHelper = AppPrefsHelper(context, "kaz", gson)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Singleton
    @Provides
    fun provideAppDataManager(prefsHelper: PrefsHelper, apiHelper: ApiHelper): DataManager = AppDataManager(prefsHelper, apiHelper)
}