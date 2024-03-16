package com.swipereveal.demo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.chauthai.swipereveallayout.databinding.RowListBinding

/**
 * Created by raquezha on 5/17/23.
 */
class RecyclerAdapter(
    private val mContext: Context,
    private var mDataSet: MutableList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val binderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RowListBinding.inflate(LayoutInflater.from(mContext))
        return ViewHolder(
            itemView = binding.root.rootView,
            swipeLayout = binding.root,
            frontLayout = binding.frontLayout,
            deleteLayout = binding.deleteLayout,
            textView = binding.text
        )
    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {
        // uncomment if you want to open only one row at a time
        // binderHelper.setOpenOnlyOne(true);

        val holder = h as ViewHolder
        if (0 <= position && position < mDataSet.size) {
            val data = mDataSet[position]

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            binderHelper.bind(holder.swipeLayout, data)

            // Bind your data here
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onSaveInstanceState]
     */
    fun saveStates(outState: Bundle?) {
        binderHelper.saveStates(outState)
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onRestoreInstanceState]
     */
    fun restoreStates(inState: Bundle?) {
        binderHelper.restoreStates(inState)
    }

    private inner class ViewHolder(
        val itemView: View,
        val swipeLayout: SwipeRevealLayout,
        private val frontLayout: View,
        private val deleteLayout: View,
        private val textView: TextView
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: String) {
            deleteLayout.setOnClickListener {
                mDataSet.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
            textView.text = data
            frontLayout.setOnClickListener {
                val displayText = "$data clicked"
                Toast.makeText(mContext, displayText, Toast.LENGTH_SHORT).show()
                Log.d("RecyclerAdapter", displayText)
            }
        }
    }
}
