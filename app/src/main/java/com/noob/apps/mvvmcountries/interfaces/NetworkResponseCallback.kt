package com.noob.apps.mvvmcountries.interfaces

interface NetworkResponseCallback {
    fun onNetworkSuccess()
    fun onNetworkFailure(th : Throwable)
}