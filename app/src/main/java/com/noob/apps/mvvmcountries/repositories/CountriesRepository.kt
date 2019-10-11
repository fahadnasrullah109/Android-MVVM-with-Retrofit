package com.noob.apps.mvvmcountries.repositories

import androidx.lifecycle.MutableLiveData
import com.noob.apps.mvvmcountries.interfaces.NetworkResponseCallback
import com.noob.apps.mvvmcountries.models.Country
import com.noob.apps.mvvmcountries.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mCountryList: MutableLiveData<List<Country>> =
        MutableLiveData<List<Country>>().apply { value = emptyList() }

    companion object {
        private var mInstance: CountriesRepository? = null
        fun getInstance(): CountriesRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = CountriesRepository()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mCountryCall: Call<List<Country>>

    fun getCountries(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<Country>> {
        mCallback = callback
        if (!mCountryList.value!!.isEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mCountryList
        }
        mCountryCall = RestClient.getInstance().getApiService().getCountries()
        mCountryCall.enqueue(object : Callback<List<Country>> {

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                mCountryList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                mCountryList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mCountryList
    }
}