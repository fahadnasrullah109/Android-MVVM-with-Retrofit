package com.noob.apps.mvvmcountries.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.noob.apps.mvvmcountries.R
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
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.countries_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: CountriesListAdapter.ViewHolder, position: Int) {
        holder.titleTv.setText(mList!!.get(position).name)
        holder.descriptionTv.setText(mList!!.get(position).region)
        SvgLoader.pluck()
            .with(holder.itemView.context as Activity?)
            .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
            .load(mList!!.get(position).flag, holder.thumbIv);
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbIv: ImageView
        var titleTv: TextView
        var descriptionTv: TextView

        init {
            thumbIv = itemView.findViewById(R.id.thumbIv)
            titleTv = itemView.findViewById(R.id.titleTv)
            descriptionTv = itemView.findViewById(R.id.descriptionTv)
        }
    }
}