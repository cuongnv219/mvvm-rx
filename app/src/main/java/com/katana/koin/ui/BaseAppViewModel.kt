package com.katana.koin.ui

import com.core.BaseViewModel
import com.katana.koin.data.DataManager
import com.utils.SchedulerProvider

abstract class BaseAppViewModel(
        val dataManager: DataManager,
        val schedulerProvider: SchedulerProvider,
) : BaseViewModel()