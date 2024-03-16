package com.newsoft.super_note.ui.task.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsoft.nscustom.view.recyclerview.BaseAdapter
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.ThemeModel
import kotlinx.android.synthetic.main.item_theme.view.*


class ThemeAdapter :
    BaseAdapter<ThemeModel.Item?, ThemeAdapter.AdapterHolder>() {

    var positionClick = 0

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): AdapterHolder {
        return AdapterHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_theme, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindView(
        holder: AdapterHolder?,
        item: ThemeModel.Item?,
        position: Int,
        size: Int
    ) {

        if (position == positionClick) {
            holder!!.lineBlue.visibility = View.VISIBLE
            holder.tvTheme.apply {
                setTextColor(ContextCompat.getColor(requireContextAdapter(), R.color.blue))
                setTypeface(null, Typeface.BOLD);
            }
        } else {
            holder!!.lineBlue.visibility = View.GONE
            holder.tvTheme.apply {
                setTextColor(ContextCompat.getColor(requireContextAdapter(), R.color.black))
                setTypeface(null, Typeface.NORMAL);
            }

        }

        holder.tvTheme.text = item!!.theme

        holder!!.linearTheme.setOnClickListener {
            positionClick = position
            notifyDataSetChanged()
        }
    }

    class AdapterHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val tvTheme = mView.tvTheme
        val linearTheme = mView.linearTheme
        val lineBlue = mView.lineBlue

    }

}