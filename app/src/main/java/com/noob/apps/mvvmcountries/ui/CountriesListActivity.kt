package com.noob.apps.mvvmcountries.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrosid.svgloader.SvgLoader
import com.noob.apps.mvvmcountries.R
import com.noob.apps.mvvmcountries.adapters.CountriesListAdapter
import com.noob.apps.mvvmcountries.databinding.ActivityCountriesListBinding
import com.noob.apps.mvvmcountries.viewmodels.CountryListViewModel

class CountriesListActivity : AppCompatActivity() {
    private lateinit var mAdapter: CountriesListAdapter
    private lateinit var mViewModel: CountryListViewModel
    private lateinit var mActivityBinding: ActivityCountriesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_countries_list)

        mViewModel = ViewModelProviders.of(this).get(CountryListViewModel::class.java)

        mActivityBinding.viewModel = mViewModel
        mActivityBinding.lifecycleOwner = this

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mActivityBinding.recyclerView.setHasFixedSize(true)
        mActivityBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = CountriesListAdapter(mViewModel.getCountriesList().value)
        mActivityBinding.recyclerView.adapter = mAdapter
    }

    private fun initializeObservers() {
        mViewModel.fetchCountriesFromServer(this, false).observe(this, Observer { kt ->
            mAdapter.setData(kt)
        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
                mActivityBinding.floatingActionButton.hide()
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
                mActivityBinding.floatingActionButton.show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SvgLoader.pluck().close();
    }
}