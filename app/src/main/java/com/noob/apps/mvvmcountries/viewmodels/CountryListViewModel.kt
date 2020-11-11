package com.noob.apps.mvvmcountries.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noob.apps.mvvmcountries.interfaces.NetworkResponseCallback
import com.noob.apps.mvvmcountries.models.Country
import com.noob.apps.mvvmcountries.repositories.CountriesRepository
import com.noob.apps.mvvmcountries.utils.NetworkHelper

class CountryListViewModel(private val app: Application) : AndroidViewModel(app) {
    private var mList: MutableLiveData<List<Country>> =
        MutableLiveData<List<Country>>().apply { value = emptyList() }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = CountriesRepository.getInstance()

    fun fetchCountriesFromServer(forceFetch: Boolean): MutableLiveData<List<Country>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getCountries(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }

    fun onRefreshClicked(view: View) {
        fetchCountriesFromServer(true)
    }
}