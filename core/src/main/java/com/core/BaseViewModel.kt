package com.core

import androidx.lifecycle.ViewModel
import com.utils.SchedulerProvider

abstract class BaseViewModel<DATA>(
        val dataManager: DATA,
        val schedulerProvider: SchedulerProvider
) : ViewModel()