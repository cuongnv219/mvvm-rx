package com.core

import androidx.lifecycle.ViewModel
import com.utils.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel(
//        val dataManager: DATA,
        val schedulerProvider: SchedulerProvider,
) : ViewModel() {

    val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}