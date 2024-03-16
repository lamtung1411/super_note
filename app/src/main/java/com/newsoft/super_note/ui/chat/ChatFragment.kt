package com.newsoft.super_note.ui.chat

import android.view.View
import com.newsoft.nscustom.ext.view.text
import com.newsoft.nscustom.view.recyclerview.RvLayoutManagerEnums
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.ChatModel
import com.newsoft.super_note.data.sqlite.DBManager
import com.newsoft.super_note.ui.base.BaseFragment
import com.newsoft.super_note.ui.chat.adapter.ChatAdapter
import com.newsoft.super_note.ui.home.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_chat.rvMessList
import kotlinx.android.synthetic.main.fragment_home.rvCategory
import kotlinx.android.synthetic.main.fragment_note.edtTitle

class ChatFragment : BaseFragment(R.layout.fragment_chat) {

    private var chatAdapter: ChatAdapter? = null
    val chatModel = ArrayList<ChatModel.Item>()

    override fun onCreate() {
        chatAdapter = ChatAdapter()

        chatModel.add(
            ChatModel.Item(
                0,
                R.drawable.ic_profile,
                "Christopher",
                "Hi, How Are you",
                "8:35 pm"
            )
        )

        chatModel.add(
            ChatModel.Item(
                1,
                R.drawable.ic_profile,
                "Christopher",
                "Hi, How Are you",
                "8:35 pm"
            )
        )

        chatModel.add(
            ChatModel.Item(
                2,
                R.drawable.ic_profile,
                "Christopher",
                "Hi, How Are you",
                "8:35 pm"
            )
        )

        chatModel.add(
            ChatModel.Item(
                3,
                R.drawable.ic_profile,
                "Christopher",
                "Hi, How Are you",
                "8:35 pm"
            )
        )

        chatModel.add(
            ChatModel.Item(
                4,
                R.drawable.ic_profile,
                "Christopher",
                "Hi, How Are you",
                "8:35 pm"
            )
        )

        chatModel.add(
            ChatModel.Item(
                5,
                R.drawable.ic_profile,
                "Christopher",
                "Hi, How Are you",
                "8:35 pm"
            )
        )

    }

    override fun onViewCreated(view: View) {

        chatAdapter?.apply {
            setRecyclerView(
                rvMessList,
                type = RvLayoutManagerEnums.LinearLayout_VERTICAL
            )
            setItems(chatModel, 0)
            setOnAdapterListener { id, item, pos ->

            }
        }
    }

}