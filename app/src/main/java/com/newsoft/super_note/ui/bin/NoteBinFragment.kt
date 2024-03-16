package com.newsoft.super_note.ui.bin


import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import com.github.onecode369.wysiwyg.WYSIWYG
import com.newsoft.nscustom.ext.context.getDataExtras

import com.newsoft.nscustom.ext.value.fromJson
import com.newsoft.nscustom.ext.value.toJson
import com.newsoft.nscustom.view.cfalertdialog.CFAlertDialog
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel

import com.newsoft.super_note.data.model.NoteModel

import com.newsoft.super_note.data.sqlite.DBManager

import com.socks.library.KLog
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.fragment_note.btnBack

import kotlinx.android.synthetic.main.fragment_note.edtTitle
import kotlinx.android.synthetic.main.fragment_note_bin.btnDeleteBin

import kotlinx.android.synthetic.main.fragment_note_bin.btnRestore
import kotlinx.android.synthetic.main.fragment_note_bin.viewShade


class NoteBinFragment : Fragment() {

    var itemCategory: CategoryModel.Item? = null
    var noteModel = NoteModel()
    var title = ""

    private var contents = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataExtras<String>("item", "").let {
            itemCategory = fromJson(it, CategoryModel.Item::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_note_bin, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteLibrary.setEditorHeight(200)
        noteLibrary.setEditorFontSize(16)
        noteLibrary.setPadding(10, 10, 10, 10)
        noteLibrary.setFocusable(false)
        noteLibrary.setEnabled(false)

        //TODO lấy dữ liệu noteModel trong DBManager.getInstance().noteItemAll từ idNote của itemCategory
        itemCategory?.let { itemCategory ->
            for (item in DBManager.getInstance().noteItemAll) {
                if (itemCategory.idNote == item.id) {
                    noteModel = item
                }
            }
        }
        KLog.json(toJson(noteModel))

        if (noteModel.content.isNotEmpty()) {
            contents = noteModel.content
            noteLibrary.html = contents
        }

        itemCategory?.let {
            if (itemCategory!!.idNote != 1) {
                title = it.title
                edtTitle.setText(it.title)
            }
        }

        edtTitle.setFocusable(false)
        edtTitle.setEnabled(false)
        edtTitle.setCursorVisible(false)
        edtTitle.setKeyListener(null)


        noteLibrary.setOnTextChangeListener(object : WYSIWYG.OnTextChangeListener {
            override fun onTextChange(text: String?) {
                Log.e("onTextChange", " $text ")
                contents = noteLibrary.html.toString()
            }
        })

        viewShade.setOnClickListener {
            openDialogRestore()
        }

        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnDeleteBin.setOnClickListener {
            openDialogDelete()
        }

        btnRestore.setOnClickListener {
            DBManager.getInstance().updateCategoryBinDelete(itemCategory!!,true)
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    private fun openDialogDelete() {
        val contentView = View.inflate(context, R.layout.dialog_delete, null)
        val dialog = CFAlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setHeaderView(contentView)
            .create()

        val btnDelete = contentView.findViewById<carbon.widget.TextView>(R.id.btnDeleteDialog)
        val btnDismiss = contentView.findViewById<carbon.widget.TextView>(R.id.btnDismiss)

        btnDelete.setOnClickListener {
            dialog.dismiss()
            DBManager.getInstance().delete(DBManager.TABLE_CATEGORY, itemCategory!!.id)
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnDismiss.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun openDialogRestore() {
        val contentView = View.inflate(context, R.layout.dialog_restore, null)
        val dialog = CFAlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setHeaderView(contentView)
            .create()

        val btnRestore = contentView.findViewById<carbon.widget.TextView>(R.id.btnRestore)
        val btnDismiss = contentView.findViewById<carbon.widget.TextView>(R.id.btnDismiss)

        btnRestore.setOnClickListener {
            dialog.dismiss()
            DBManager.getInstance().updateCategoryBinDelete(itemCategory!!,true)
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnDismiss.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            dialog.dismiss()
        }

        dialog.show()
    }


}

