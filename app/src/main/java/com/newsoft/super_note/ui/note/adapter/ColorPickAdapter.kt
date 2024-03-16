package com.newsoft.super_note.ui.note.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsoft.nscustom.view.recyclerview.BaseAdapter
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.PickColorModel
import com.newsoft.super_note.data.model.ThemeModel
import kotlinx.android.synthetic.main.item_color.view.*
import kotlinx.android.synthetic.main.item_theme.view.*


class ColorPickAdapter :
    BaseAdapter<PickColorModel.Item?, ColorPickAdapter.AdapterHolder>() {

//    var positionClick = 8

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): AdapterHolder {
        return AdapterHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_color, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindView(
        holder: AdapterHolder?,
        item: PickColorModel.Item?,
        position: Int,
        size: Int
    ) {
        holder!!.imgCircleColor.setBackgroundColor(
            ContextCompat.getColor(
                requireContextAdapter(),
                item!!.colorText
            )
        )
//        if (position == positionClick) {
//            holder.imgTick.visibility = View.VISIBLE
//        } else {
//            holder.imgTick.visibility = View.GONE
//        }
//        Log.e("positionClick","$positionClick")

        holder.imgCircleColor.setOnClickListener {
//            positionClick = position
            mOnAdapterListener!!.onItemClick(0,item,position)
            notifyDataSetChanged()
        }

    }

    class AdapterHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgCircleColor = mView.imgCircleColor
//        val imgTick = mView.imgTick
//        val contraintPickColor = mView.contraintPickColor
    }
}


