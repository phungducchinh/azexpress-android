package com.lib.core.composite

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

abstract class BaseComposite(
    private val compositeDisposable: CompositeDisposable
) : DisposableContainer {

    override
    fun  add(d: Disposable): Boolean {
        return compositeDisposable.add(d)
    }

    fun addAll(vararg ds: Disposable): Boolean {
        return compositeDisposable.addAll(*ds)
    }

    override
    fun  remove(d: Disposable): Boolean {
        return compositeDisposable.remove(d)
    }

    override
    fun  delete(d: Disposable): Boolean {
        return compositeDisposable.delete(d)
    }

    fun size(): Int {
        return compositeDisposable.size()
    }

    fun clear() {
        compositeDisposable.clear()
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

    fun isDisposed(): Boolean {
        return compositeDisposable.isDisposed
    }
}