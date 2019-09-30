package com.core

import androidx.lifecycle.ViewModel
import com.utils.SchedulerProvider

interface KViewModel<out Input, out Output> {
    val input: Input
    val output: Output
}

abstract class BaseViewModel<IN, OUT, DATA>(
        val dataManager: DATA,
        val schedulerProvider: SchedulerProvider
) : ViewModel(), KViewModel<IN, OUT>