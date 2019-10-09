package com.utils

import android.util.Log
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

class BasicDisposeBag : DisposeBag, Disposable {

    private var stackTrace: Throwable? = null

    companion object {
        const val LOG_TAG = "DisposeBag"
    }

    private val disposables = mutableListOf<WeakReference<Disposable>>()
    private var isDisposed = false

    override fun add(disposable: Disposable) {
        synchronized(disposables) {
            disposables.cleanUp()
            disposables.add(WeakReference(disposable))
        }
    }

    override fun add(vararg disposables: Disposable) {
        disposables.forEach {
            add(it)
        }
    }

    override fun remove(disposable: Disposable) {
        synchronized(disposables) {
            disposables.cleanUp()
            disposables.removeAll { it.get() == disposable }
        }
    }

    override fun dispose() {
        synchronized(disposables) {
            this.isDisposed = true
            disposables.asSequence()
                    .mapNotNull { it.get() }
                    .mapNotNull { if (it.isDisposed) null else it }
                    .forEach { it.dispose() }
            disposables.clear()
        }
    }

    override fun isDisposed(): Boolean {
        synchronized(disposables) {
            return isDisposed
        }
    }


    private fun MutableList<WeakReference<Disposable>>.cleanUp() {
        this.removeAll { it.get() == null }
    }


    init {
        if (DisposeBag.verbose) {
            stackTrace = Throwable()
        }
    }

    fun finalize() {
        if (isDisposed) return

        synchronized(disposables) {
            if (isDisposed) return

            Log.w(LOG_TAG, "Leaking DisposeBag detected. Make sure to call .dispose() when" +
                    " the object is no longer needed. Disposing all references now.")

            stackTrace?.printStackTrace()

            dispose()
        }
    }
}