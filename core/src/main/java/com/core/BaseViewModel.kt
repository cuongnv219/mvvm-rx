package com.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {

    val viewModelScopeExceptionHandler by lazy { viewModelScope + exceptionHandler }

    val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private val exceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                throwable.printStackTrace()
            }
        }
    }
}