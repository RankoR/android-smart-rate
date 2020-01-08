package com.g2pdev.smartrate.demo.util

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.schedulersIoToMain(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.schedulersIoToMain(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.schedulersSingleToMain(): Single<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.schedulersSingleToMain(): Completable {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}
