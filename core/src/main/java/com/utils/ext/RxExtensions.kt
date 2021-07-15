package com.utils.ext

import com.utils.DisposeBag
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.toObservable

fun <G : Iterable<T>, T : Any, U : Any> Observable<G>.mapIterable(block: (T) -> U)
        : Observable<List<U>> {
    return this.map {
        it.map(block)
    }
}

fun <T : Any> Observable<Optional<T>>.filterNotNull(): Observable<T> {
    return this.flatMap {
        val unBoxed = it.raw
        val list = mutableListOf<T>()
        if (unBoxed != null) list.add(unBoxed)
        list.toObservable()
    }
}

/**
 * Filters null values using the [filterNotNull] function.
 */
fun <T : Any, U : Any> Observable<T>.mapNotNull(block: (T) -> U?): Observable<U> = this
        .map { block(it).box() }
        .filterNotNull()


//fun <T> Observable<T>.onErrorComplete(): Observable<T> = this.onErrorResumeNext(Obser.empty())

fun <T : Disposable> T.disposedBy(disposeBag: DisposeBag): T {
    disposeBag.add(this)
    return this
}

class Optional<out T> internal constructor(val raw: T?)

fun <T> T?.box(): Optional<T> = Optional(this)