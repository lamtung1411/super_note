package com.newsoft.super_note.ui.task.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUS_DOWN
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsoft.nscustom.ext.view.onTextChangeListener
import com.newsoft.nscustom.view.recyclerview.BaseAdapter
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.TaskModel
import kotlinx.android.synthetic.main.item_task.view.*


class TaskAdapter :
    BaseAdapter<TaskModel.Item?, TaskAdapter.AdapterHolder>() {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): AdapterHolder {
        return AdapterHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_task, parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindView(
        holder: AdapterHolder?,
        item: TaskModel.Item?,
        position: Int,
        size: Int
    ) {

        holder!!.edtContent.setText(item!!.content)
        holder.checkboxList.isChecked = item.tick

        if (item.tick) {
            holder.edtContent.paintFlags =
                holder.edtContent.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.edtContent.paintFlags =
                holder.edtContent.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        if (position == itemCount - 1) {
            holder.btnAdd.visibility = View.VISIBLE
        } else {
            holder.btnAdd.visibility = View.GONE
        }

        holder.edtContent.onTextChangeListener(onTextChanged = {
            if (item.content != it && item.hasFocus) {
                items[position]!!.content = it
            }
        })

        holder.edtContent.setOnFocusChangeListener { v, hasFocus ->
            try {
                items[position]!!.hasFocus = hasFocus
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        holder.checkboxList.setOnClickListener {
            items[position]!!.content = holder.edtContent.text.toString()
            items[position]!!.tick = holder.checkboxList.isChecked
            notifyItemChanged(position)
        }

        holder.btnDelete.setOnClickListener {
//            Log.e("btnDelete", "$position")
            mOnAdapterListener!!.onItemClick(1, item, position)
        }

        holder.btnAdd.setOnClickListener {
            mOnAdapterListener!!.onItemClick(2, item, position)
            holder.edtContent.requestFocus(FOCUS_DOWN)
        }
    }

    class AdapterHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val edtContent = mView.edtContent
        val btnDelete = mView.btnDelete
        val checkboxList = mView.checkboxList
        val btnAdd = mView.btnAdd

    }

}