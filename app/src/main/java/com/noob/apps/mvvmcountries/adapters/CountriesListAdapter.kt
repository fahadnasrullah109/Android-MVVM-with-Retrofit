package com.noob.apps.mvvmcountries.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.noob.apps.mvvmcountries.R
import com.noob.apps.mvvmcountries.databinding.CountriesListItemBinding
import com.noob.apps.mvvmcountries.models.Country

class CountriesListAdapter(var mList: List<Country>? = listOf()) :
    RecyclerView.Adapter<CountriesListAdapter.ViewHolder>() {

    fun setData(list: List<Country>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountriesListAdapter.ViewHolder {
        val binding: CountriesListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.countries_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: CountriesListAdapter.ViewHolder, position: Int) {
        holder.itemBinding.country = mList!!.get(position)
    }

    class ViewHolder(var itemBinding: CountriesListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }
}