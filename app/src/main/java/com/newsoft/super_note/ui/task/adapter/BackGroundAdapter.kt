package com.newsoft.super_note.ui.task.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsoft.nscustom.view.recyclerview.BaseAdapter
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.BackGroundModel
import kotlinx.android.synthetic.main.item_background.view.*


class BackGroundAdapter :
    BaseAdapter<BackGroundModel.Item?, BackGroundAdapter.AdapterHolder>() {



    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): AdapterHolder {
        return AdapterHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_background, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindView(
        holder: AdapterHolder?,
        item: BackGroundModel.Item?,
        position: Int,
        size: Int
    ) {
        holder!!.imgBackGround.setImageDrawable(
            ContextCompat.getDrawable(
                requireContextAdapter(),
                item!!.imgBackGround
            )
        )
    }

    class AdapterHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgBackGround = mView.imgBackGround

    }

}