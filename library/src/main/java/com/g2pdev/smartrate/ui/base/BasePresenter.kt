package com.g2pdev.smartrate.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<T> : MvpPresenter<T>() where T : MvpView {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private fun dispose() {
        compositeDisposable.dispose()
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose()
    }

    fun Disposable.disposeOnDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

}