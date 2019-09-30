package com.utils

import io.reactivex.disposables.Disposable

interface DisposeBag : Disposable {

    fun add(disposable: Disposable)

    fun remove(disposable: Disposable)

    companion object {
        fun create(): DisposeBag = BasicDisposeBag()

        var verbose = false
    }
}

