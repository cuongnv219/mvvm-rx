package com.utils

import io.reactivex.rxjava3.disposables.Disposable

interface DisposeBag : Disposable {

    fun add(disposable: Disposable)

    fun add(vararg disposables: Disposable)

    fun remove(disposable: Disposable)

    companion object {
        fun create(): DisposeBag = BasicDisposeBag()

        var verbose = false
    }
}

