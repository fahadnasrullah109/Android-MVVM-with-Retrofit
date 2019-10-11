package com.noob.apps.mvvmcountries.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.noob.apps.mvvmcountries.R
import com.noob.apps.mvvmcountries.adapters.CountriesListAdapter
import com.noob.apps.mvvmcountries.viewmodels.CountryListViewModel

class CountriesListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mFloatingBtn: FloatingActionButton
    private lateinit var mAdapter: CountriesListAdapter
    private lateinit var mViewModel: CountryListViewModel
    private var mForceRefreshing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries_list)

        mProgressBar = findViewById(R.id.progressBar)
        mRecyclerView = findViewById(R.id.recyclerView)
        mFloatingBtn = findViewById(R.id.floatingActionButton)
        mFloatingBtn.setOnClickListener(this)

        mViewModel = ViewModelProviders.of(this).get(CountryListViewModel::class.java)

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = CountriesListAdapter(mViewModel.getCountriesList().value)
        mRecyclerView.adapter = mAdapter
    }

    private fun initializeObservers() {
        mForceRefreshing = false
        mViewModel.fetchCountriesFromServer(this, mForceRefreshing).observe(this, Observer { kt ->
            mAdapter.setData(kt)
            if (mForceRefreshing) {
                mRecyclerView.smoothScrollToPosition(0)
            }
        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mProgressBar.visibility = View.VISIBLE
                mFloatingBtn.hide()
            } else {
                mProgressBar.visibility = View.GONE
                mFloatingBtn.show()
            }
        })
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.floatingActionButton) {
            mForceRefreshing = true
            mViewModel.fetchCountriesFromServer(this, mForceRefreshing)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SvgLoader.pluck().close();
    }
}