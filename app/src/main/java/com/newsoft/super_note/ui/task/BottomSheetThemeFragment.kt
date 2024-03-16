package com.newsoft.super_note.ui.task

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsoft.nscustom.view.recyclerview.RvLayoutManagerEnums
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.BackGroundModel
import com.newsoft.super_note.data.model.ThemeModel
import com.newsoft.super_note.ui.task.adapter.BackGroundAdapter

import com.newsoft.super_note.ui.task.adapter.ThemeAdapter

class BottomSheetThemeFragment() : BottomSheetDialogFragment() {

    private var themeAdapter: ThemeAdapter? = null
    private var backGroundAdapter: BackGroundAdapter? = null
    private var listener: CallBackChangeBackGroundList? = null

    fun setListene (listener: CallBackChangeBackGroundList)  {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialogTheme)

        themeAdapter = ThemeAdapter()
        backGroundAdapter = BackGroundAdapter()
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.bottom_dialog_theme, null)
        dialog.setContentView(contentView)

        val recyclerViewTheme = contentView.findViewById<RecyclerView>(R.id.rvTheme)
        val recyclerViewBackground = contentView.findViewById<RecyclerView>(R.id.rvBackGround)


        val themeModel = ArrayList<ThemeModel.Item>()
        themeModel.add(ThemeModel.Item(0, "Color"))
        themeModel.add(ThemeModel.Item(1, "Architecture"))
        themeModel.add(ThemeModel.Item(2, "Art"))
        themeModel.add(ThemeModel.Item(3, "Nature"))
        themeModel.add(ThemeModel.Item(4, "Paper"))
        themeModel.add(ThemeModel.Item(5, "Simplify"))
        themeModel.add(ThemeModel.Item(6, "Technology"))
        themeModel.add(ThemeModel.Item(7, "Love"))
        themeModel.add(ThemeModel.Item(8, "Water color"))


        themeAdapter?.apply {
            setRecyclerView(
                recyclerViewTheme,
                type = RvLayoutManagerEnums.LinearLayout_HORIZONTAL
            )
            setItems(themeModel, 0)
            setOnAdapterListener { id, item, pos ->

            }
        }

        val backGroundModel = ArrayList<BackGroundModel.Item>()
        backGroundModel.add(
            BackGroundModel.Item(
                0, R.drawable.bg_blue, R.color.blueactionbar,R.color.bluebackground
            )
        )
        backGroundModel.add(
            BackGroundModel.Item(
                1, R.drawable.bg_yellow, R.color.yellow,R.color.yellowbackground
            )
        )

        backGroundAdapter?.apply {
            setRecyclerView(recyclerViewBackground, type = RvLayoutManagerEnums.LinearLayout_HORIZONTAL)
            setItems(backGroundModel, 0)
            setOnAdapterListener { id, item, pos ->
                listener!!.onImgBackGround(item!!.colorBar,item.colorBackGround)
                dialog.dismiss()
            }
        }

        val window = dialog.window
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }
}
