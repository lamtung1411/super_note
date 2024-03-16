package com.newsoft.super_note.ui.chat.adapter

import android.annotation.SuppressLint
import android.util.Log
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
import com.newsoft.super_note.data.model.ChatModel
import kotlinx.android.synthetic.main.item_category.view.btnNew
import kotlinx.android.synthetic.main.item_category.view.linear
import kotlinx.android.synthetic.main.item_messenger.view.imgAva
import kotlinx.android.synthetic.main.item_messenger.view.tvLastMessage
import kotlinx.android.synthetic.main.item_messenger.view.tvName
import kotlinx.android.synthetic.main.item_messenger.view.tvTime
import java.util.Calendar

class ChatAdapter: BaseAdapter<ChatModel.Item?, ChatAdapter.AdapterHolder>() {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): AdapterHolder {
        return AdapterHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_messenger, parent, false)
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindView(
        holder: AdapterHolder?,
        item: ChatModel.Item?,
        position: Int,
        size: Int
    ) {
        holder!!.imgAva.setImageDrawable(
            ContextCompat.getDrawable(
                requireContextAdapter(), item!!.avatar
            )
        )

        holder.tvName.text = item.name
        holder.tvLastMessage.text = item.message
        holder.tvTime.text = item.time

    }

    class AdapterHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgAva = mView.imgAva
        val tvName = mView.tvName
        val tvLastMessage = mView.tvLastMessage
        val tvTime = mView.tvTime

    }
}