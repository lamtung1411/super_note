package com.newsoft.super_note.ui.notification.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsoft.nscustom.view.recyclerview.BaseAdapter
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.SettingModel
import kotlinx.android.synthetic.main.item_setting.view.*


class SettingAdapter :
    BaseAdapter<SettingModel.Item?, SettingAdapter.AdapterHolder>() {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): AdapterHolder {
        return AdapterHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_setting, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindView(
        holder: AdapterHolder?,
        item: SettingModel.Item?,
        position: Int,
        size: Int
    ) {
        holder!!.imgIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContextAdapter(),item!!.icon))
        holder.tvTitle.text = item.title
    }

    class AdapterHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgIcon = mView.imgIcon
        val tvTitle = mView.tvTitle

    }

}