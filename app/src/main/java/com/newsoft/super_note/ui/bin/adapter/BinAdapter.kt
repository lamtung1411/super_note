package com.newsoft.super_note.ui.notification.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.newsoft.nscustom.view.recyclerview.BaseAdapter
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import kotlinx.android.synthetic.main.item_bin.view.btnRestore
import kotlinx.android.synthetic.main.item_bin.view.swipe_layout_bin
import kotlinx.android.synthetic.main.item_category.view.*


class BinAdapter :
    BaseAdapter<CategoryModel.Item?, BinAdapter.AdapterHolder>() {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): AdapterHolder {
        return AdapterHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_bin, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindView(
        holder: AdapterHolder?,
        item: CategoryModel.Item?,
        position: Int,
        size: Int
    ) {
        holder!!.imgIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContextAdapter(), item!!.image))

        holder.tvTitle.text = item.title

        holder.swipe_layout_bin.showMode = SwipeLayout.ShowMode.PullOut
        holder.swipe_layout_bin.addDrag(
            SwipeLayout.DragEdge.Right,
            holder.swipe_layout_bin.findViewById<LinearLayout>(R.id.linearSwipeLayoutBin)
        )

        holder.linear.setBackgroundColor(
            ContextCompat.getColor(
                requireContextAdapter(),item.color))

        holder.linear.setOnClickListener {
            mOnAdapterListener!!.onItemClick(1, item, position)
            holder.swipe_layout_bin.close()
        }

        holder.btnRestore.setOnClickListener{
            mOnAdapterListener!!.onItemClick(2, item, position)
            holder.swipe_layout_bin.close()
            items.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }

        holder.btnDelete.setOnClickListener {
            mOnAdapterListener!!.onItemClick(3, item, position)
            holder.swipe_layout_bin.close()
            items.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }


    }

    class AdapterHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgIcon = mView.imgIcon
        val tvTitle = mView.tvTitle
        val linear = mView.linear
        val btnRestore = mView.btnRestore
        val btnDelete = mView.btnDelete
        val swipe_layout_bin = mView.swipe_layout_bin

    }

}