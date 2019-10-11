package com.noob.apps.mvvmcountries.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkHelper {
    companion object {
        fun isOnline(context: Context) : Boolean {
            val connectivityManager  : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return  networkInfo != null && networkInfo.isConnected
        }
    }
}