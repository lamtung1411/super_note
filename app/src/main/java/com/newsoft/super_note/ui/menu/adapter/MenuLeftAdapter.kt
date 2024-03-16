package com.newsoft.super_note.ui.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.MenuItem
import com.newsoft.super_note.ui.menu.CallBackChangeMenu
import kotlinx.android.synthetic.main.item_menu_left.view.*


class MenuLeftAdapter : RecyclerView.Adapter<MenuLeftAdapter.AnswerHolder> {

    var context: Context
    var items: ArrayList<MenuItem>
    var listener: CallBackChangeMenu? = null

    fun setListene (listener: CallBackChangeMenu)  {
        this.listener = listener
    }

    constructor(context: Context, listener: CallBackChangeMenu) : super() {
        this.context = context
        this.listener = listener
        this.items = ArrayList()
    }

    fun setItems(items: List<MenuItem>) {
        this.items = items as ArrayList<MenuItem>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        return AnswerHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_left, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {

        val item = items[position]

        holder.tvBtn.text = item.title
        holder.imgIcon.setImageDrawable(ContextCompat.getDrawable(context,item.icon))
        holder.tvBtn.setTextColor(ContextCompat.getColor(context, item.color))
        holder.itemView.setOnClickListener {
            listener?.onMenuHome(item)
        }

    }

    class AnswerHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val tvBtn = mView.tvBtn
        val imgIcon = mView.imgIcon
    }
}