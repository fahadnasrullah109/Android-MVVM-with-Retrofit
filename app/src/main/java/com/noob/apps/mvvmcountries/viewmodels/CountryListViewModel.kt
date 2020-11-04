package com.noob.apps.mvvmcountries.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noob.apps.mvvmcountries.interfaces.NetworkResponseCallback
import com.noob.apps.mvvmcountries.models.Country
import com.noob.apps.mvvmcountries.repositories.CountriesRepository
import com.noob.apps.mvvmcountries.utils.NetworkHelper

class CountryListViewModel : ViewModel() {
    private var mList: MutableLiveData<List<Country>> =
        MutableLiveData<List<Country>>().apply { value = emptyList() }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = CountriesRepository.getInstance()

    fun getCountriesList() = mList

    fun fetchCountriesFromServer(context: Context, forceFetch : Boolean): MutableLiveData<List<Country>> {
        if (NetworkHelper.isOnline(context)) {
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

    fun onRefreshClicked(view : View){
        fetchCountriesFromServer(view.context, true)
    }
}