package com.newsoft.super_note.ui.home.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.newsoft.nscustom.ext.value.toJson

import com.newsoft.nscustom.view.recyclerview.BaseAdapter
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import com.socks.library.KLog
import kotlinx.android.synthetic.main.item_category.view.*
import java.util.Calendar


class CategoryAdapter :
    BaseAdapter<CategoryModel.Item?, CategoryAdapter.AdapterHolder>() {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): AdapterHolder {
        return AdapterHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_category, parent, false)
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindView(
        holder: AdapterHolder?,
        item: CategoryModel.Item?,
        position: Int,
        size: Int
    ) {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Tháng bắt đầu từ 0 (tháng 0 là tháng 1)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        holder!!.imgIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContextAdapter(), item!!.image
            )
        )

        holder.swipe_layout.showMode = SwipeLayout.ShowMode.PullOut
        holder.swipe_layout.addDrag(
            SwipeLayout.DragEdge.Right,
            holder.swipe_layout.findViewById<LinearLayout>(R.id.linearSwipeLayout)
        )

        holder.tvTitle.text = item.title

        if (item.icon == 0) {
            holder.imgIconBtnNew.visibility = View.INVISIBLE
            holder.tvTime.text = "$day" + "/" + "$month" + "/" + "$year"
        } else {
            holder.imgIconBtnNew.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContextAdapter(), item.icon
                )
            )
            holder.tvTime.visibility = View.INVISIBLE
        }

        holder.linear.setBackgroundColor(
            ContextCompat.getColor(
                requireContextAdapter(), item.color
            )
        )

        holder.swipe_layout.isSwipeEnabled = item.lock

        if (item.colorButton == 0) {
            holder.btnNew.visibility = View.INVISIBLE
        } else {
            holder.btnNew.setBackgroundColor(
                ContextCompat.getColor(
                    requireContextAdapter(), item.colorButton
                )
            )
        }

        holder.btnPin.setOnClickListener {
            mOnAdapterListener!!.onItemClick(2, item, position)
            notifyDataSetChanged()
            holder.swipe_layout.close()
        }

        if (item.timeCountPin != 0L){
            holder.imgPin.visibility = View.VISIBLE
            holder.btnPin.text = "Bỏ ghim"
        } else {
            holder.imgPin.visibility = View.INVISIBLE
            holder.btnPin.text = "Ghim"
        }

        holder.btnShare.setOnClickListener {
            Log.e("share", "share")
            holder.swipe_layout.close()
        }

        holder.btnDelete.setOnClickListener {
            holder.swipe_layout.close()
            mOnAdapterListener!!.onItemClick(4, item, position)
            items.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }

        holder.linear.setOnClickListener {
            holder.swipe_layout.close()
            mOnAdapterListener!!.onItemClick(1, item, position)
        }

    }

    class AdapterHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgIcon = mView.imgIcon
        val tvTitle = mView.tvTitle
        val imgIconBtnNew = mView.imgIconBtnNew
        val linear = mView.linear
        val btnNew = mView.btnNew
        val swipe_layout = mView.swipe_layout
        val btnPin = mView.btnPin
        val btnShare = mView.btnShare
        val btnDelete = mView.btnDelete
        val tvTime = mView.tvTime
        val imgPin = mView.imgPin
    }


}