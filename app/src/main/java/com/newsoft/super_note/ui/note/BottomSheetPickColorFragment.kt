package com.newsoft.super_note.ui.note

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.PickColorModel

import com.newsoft.super_note.ui.note.adapter.ColorPickAdapter

class BottomSheetPickColorFragment() : BottomSheetDialogFragment() {

    private var colorPickAdapter: ColorPickAdapter? = null
    private var listener: CallBackChangeNote? = null
    var positionClickColor = 0

    fun setListene (listener: CallBackChangeNote)  {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialogTheme)

        colorPickAdapter = ColorPickAdapter()
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.bottom_dialog_pick_color, null)
        dialog.setContentView(contentView)


        val recyclerViewPickColor = contentView.findViewById<RecyclerView>(R.id.rvPickColor)

        val pickColorModel = ArrayList<PickColorModel.Item>()
        pickColorModel.add(PickColorModel.Item(0, R.color.orangecolorpick))
        pickColorModel.add(PickColorModel.Item(1, R.color.pinkcolorpick))
        pickColorModel.add(PickColorModel.Item(2, R.color.purple1colorpick))
        pickColorModel.add(PickColorModel.Item(3, R.color.purple2colorpick))
        pickColorModel.add(PickColorModel.Item(4, R.color.purple3colorpick))
        pickColorModel.add(PickColorModel.Item(5, R.color.blue1colorpick))
        pickColorModel.add(PickColorModel.Item(6, R.color.blue2colorpick))
        pickColorModel.add(PickColorModel.Item(7, R.color.greencolorpick))
        pickColorModel.add(PickColorModel.Item(8, R.color.black))
        pickColorModel.add(PickColorModel.Item(9, R.color.yellowcolorpick))

        colorPickAdapter?.apply {
//            positionClick = positionClickColor
            setRecyclerView(
                recyclerViewPickColor,
                layoutManager =  GridLayoutManager(context, 5)
            )
            setItems(pickColorModel, 0)
            setOnAdapterListener { id, item, pos ->
                listener!!.onColorText(item!!.colorText)
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
