package com.swipereveal.demo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.chauthai.swipereveallayout.R
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.chauthai.swipereveallayout.databinding.RowListBinding

/**
 * Created by raquezha on 5/17/23.
 */
class ListAdapter(
    private val context: Context?,
    objects: List<String?>?
) : ArrayAdapter<String?>(
    context!!,
    R.layout.row_list,
    objects!!
) {
    private val binderHelper: ViewBinderHelper = ViewBinderHelper()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder = ViewHolder()
        val binding = if(convertView == null) {
            RowListBinding.inflate(LayoutInflater.from(context))
        } else {
            RowListBinding.bind(convertView)
        }
        holder.swipeLayout = binding.swipeLayout
        holder.frontView = binding.frontLayout
        holder.deleteView = binding.deleteLayout
        holder.textView = binding.text
        binding.root.tag = holder

        val item = getItem(position)
        if (item != null) {
            binderHelper.bind(holder.swipeLayout, item)
            holder.textView.text = item
            holder.deleteView.setOnClickListener { remove(item) }
            holder.frontView.setOnClickListener {
                val displayText = "$item clicked"
                Toast.makeText(context, displayText, Toast.LENGTH_SHORT).show()
                Log.d("ListAdapter", displayText)
            }
        }
        return binding.root
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

    private class ViewHolder {
        lateinit var swipeLayout: SwipeRevealLayout
        lateinit var frontView: View
        lateinit var deleteView: View
        lateinit var textView: TextView
    }
}
