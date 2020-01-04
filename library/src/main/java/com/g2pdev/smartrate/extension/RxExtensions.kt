package com.g2pdev.smartrate.extension

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.schedulersIoToMain(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.schedulersIoToMain(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

internal fun Completable.schedulersIoToMain(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

internal fun <T> Maybe<T>.schedulersIoToMain(): Maybe<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

internal fun <T> Single<T>.schedulersSingleToMain(): Single<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

internal fun <T> Observable<T>.schedulersSingleToMain(): Observable<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

internal fun Completable.schedulersSingleToMain(): Completable {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

internal fun <T> Maybe<T>.schedulersSingleToMain(): Maybe<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}