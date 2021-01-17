package com.g2pdev.smartrate.logic.model

enum class Store(internal var appID: String) {

    GOOGLE_PLAY(""),
    AMAZON(""),
    XIAOMI(""),
    SAMSUNG(""),
    APP_GALLERY("");
//    APTOIDE // TODO: Don't know how to link with package name
    fun setAppId(appID: String): Store {
        this.appID = appID
        return this
    }
}
