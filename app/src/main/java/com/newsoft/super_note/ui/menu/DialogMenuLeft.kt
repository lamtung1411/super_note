package com.newsoft.super_note.ui.menu

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsoft.nscustom.ext.context.getDataExtras
import com.newsoft.nscustom.ext.context.switchFragmentBackStack
import com.newsoft.nscustom.ext.value.fromJson
import com.newsoft.nscustom.ext.value.fromJsonArray
import com.newsoft.nscustom.ext.value.toJson

import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.MenuItem
import com.newsoft.super_note.ui.home.BinFragment
import com.newsoft.super_note.ui.menu.adapter.MenuLeftAdapter
import com.newsoft.super_note.ui.task.CallBackChangeBackGroundList
import com.newsoft.super_note.utils.container
import com.socks.library.KLog
import render.animations.Render
import render.animations.Slide

class DialogMenuLeft() :
    DialogFragment(),
    CallBackChangeMenu {

    private var menuLeftAdapter: MenuLeftAdapter? = null
    private var render: Render? = null
    private var menuItems: List<MenuItem>? = null
    private var listener: CallBackChangeMenu? = null

    fun setListene (listener: CallBackChangeMenu)  {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        render = Render(requireContext())
        menuLeftAdapter = MenuLeftAdapter(requireContext(), this)

        getDataExtras<String>("menuItems", "").let {
            val menuItems = fromJsonArray(it, MenuItem::class.java)
            this.menuItems = menuItems
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.dialog_menu_left, null)
        setCancelable(true)
        dialog.setContentView(contentView)

        val rvMenuList = dialog.findViewById<RecyclerView>(R.id.rvMenuList)

        rvMenuList.layoutManager = LinearLayoutManager(requireContext())
        rvMenuList.adapter = menuLeftAdapter
        rvMenuList.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))

        menuLeftAdapter?.setItems(menuItems!!)

        val linearMenuLeft = dialog.findViewById<ConstraintLayout>(R.id.linearMenuLeft)

        linearMenuLeft.setOnClickListener {
            render?.setAnimation(Slide().OutRight(linearMenuLeft))
            render?.setDuration(300L)
            render?.start()
            Handler(Looper.myLooper()!!).postDelayed({
                dialog.dismiss()
            }, 300L)
        }

        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.show()
    }

    override fun onMenuHome(menuItem: MenuItem) {
        listener!!.onMenuHome(menuItem)
        dialog!!.dismiss()
    }

}