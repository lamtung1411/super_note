package com.newsoft.super_note.ui.note

import android.graphics.Color
import android.icu.util.Calendar
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.github.onecode369.wysiwyg.WYSIWYG
import com.newsoft.nscustom.ext.context.getDataExtras
import com.newsoft.nscustom.ext.context.newInstance
import com.newsoft.nscustom.ext.value.fromJson
import com.newsoft.nscustom.ext.value.toJson
import com.newsoft.nscustom.view.cfalertdialog.CFAlertDialog
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.MenuItem
import com.newsoft.super_note.data.model.NoteModel
import com.newsoft.super_note.data.sqlite.DBManager
import com.newsoft.super_note.ui.base.BaseFragment
import com.newsoft.super_note.ui.menu.CallBackChangeMenu
import com.newsoft.super_note.ui.menu.DialogMenuLeft
import com.newsoft.super_note.ui.task.BottomSheetThemeFragment
import com.newsoft.super_note.ui.task.CallBackChangeBackGroundList
import com.newsoft.super_note.ui.menu.adapter.MenuLeftAdapter
import com.socks.library.KLog
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.fragment_note.btnBack
import kotlinx.android.synthetic.main.fragment_note.btnMenuRightList
import kotlinx.android.synthetic.main.fragment_note.btnSave
import kotlinx.android.synthetic.main.fragment_note.edtTitle
import kotlinx.android.synthetic.main.fragment_note.linearMenuTop
import render.animations.Render


class NoteFragment : BaseFragment(R.layout.fragment_note),
    CallBackChangeMenu, CallBackChangeBackGroundList, CallBackChangeNote {

    private var menuLeftAdapter: MenuLeftAdapter? = null
    private var render: Render? = null
    var itemCategory: CategoryModel.Item? = null
    var noteModel = NoteModel()

    private var contents = ""
    private var contentsOld = ""
    var title = ""

    private var mapStyleText = HashMap<WYSIWYG.Type, ImageView>()

    fun checkTypeChoose(btnChoose: WYSIWYG.Type, types: List<WYSIWYG.Type>): Boolean {
        var isExist = false
        for (type in types) {
            if (btnChoose == type)
                isExist = true
        }
        return isExist
    }

    override fun onCreate() {

        menuLeftAdapter = MenuLeftAdapter(requireContext(), this)
//        textSizeAdapter = TextSizeAdapter(requireContext(), this)
        render = Render(requireContext())
        itemCategory = CategoryModel.Item()


        getDataExtras<String>("item", "").let {
            val itemCategory = fromJson(it, CategoryModel.Item::class.java)
            if (itemCategory != null)
                this.itemCategory = itemCategory
        }
    }

    override fun onViewCreated(view: View) {
        noteLibrary.setEditorHeight(200)
        noteLibrary.setEditorFontSize(16)
        noteLibrary.setPadding(10, 10, 10, 10)
        noteLibrary.setPlaceholder("Insert your notes here...")

        //TODO lấy dữ liệu noteModel trong DBManager.getInstance().getNote(id) từ idNote của itemCategory
        itemCategory?.let { itemCategory ->
            noteModel = DBManager.getInstance().getNote(itemCategory.idNote)

            if (itemCategory!!.idNote != 1) {
                title = itemCategory.title
                edtTitle.setText(title)
            }
        }
        KLog.json(toJson(noteModel))

        if (noteModel.content.isNotEmpty()) {
            contents = noteModel.content
            noteLibrary.html = contents
            contentsOld = noteModel.content
        }

        if (noteModel.colorBar != 0 || noteModel.colorBackGround != 0) {
            noteModel.let { noteModel ->
                linearMenuTop.setBackgroundColor(resources.getColor(noteModel.colorBar))
                noteLibrary.setBackgroundColor(resources.getColor(noteModel.colorBackGround))
                horizontalScrollView.setBackgroundColor(resources.getColor(noteModel.colorBar))
            }
        }

        if (noteModel.colorText != 0) {
            noteLibrary.setTextColor(resources.getColor(noteModel.colorText))
        } else {
            noteLibrary.setTextColor(resources.getColor(R.color.black))
        }

        mapStyleText[WYSIWYG.Type.BOLD] = btnBold
        mapStyleText[WYSIWYG.Type.ITALIC] = btnItalic
//        mapStyleText[WYSIWYG.Type.SUBSCRIPT] = btnSubscript
//        mapStyleText[WYSIWYG.Type.SUPERSCRIPT] = btnSuperScript
        mapStyleText[WYSIWYG.Type.STRIKETHROUGH] = btnStrikeThrough
        mapStyleText[WYSIWYG.Type.UNDERLINE] = btnUnderline
        mapStyleText[WYSIWYG.Type.H1] = btnHeading1
        mapStyleText[WYSIWYG.Type.H2] = btnHeading2
        mapStyleText[WYSIWYG.Type.H3] = btnHeading3
        mapStyleText[WYSIWYG.Type.H4] = btnHeading4
        mapStyleText[WYSIWYG.Type.H5] = btnHeading5
        mapStyleText[WYSIWYG.Type.H6] = btnHeading6
        mapStyleText[WYSIWYG.Type.JUSTIFYCENTER] = btnAlignCenter
        mapStyleText[WYSIWYG.Type.JUSTIFYFULL] = btnAlignJustify
        mapStyleText[WYSIWYG.Type.JUSTUFYLEFT] = btnAlignLeft
        mapStyleText[WYSIWYG.Type.JUSTIFYRIGHT] = btnAlignRight
        mapStyleText[WYSIWYG.Type.UNORDEREDLIST] = btnInsertBullets
        mapStyleText[WYSIWYG.Type.ORDEREDLIST] = btnInsertNumbers
        mapStyleText[WYSIWYG.Type.P] = btnBlockQuote
        mapStyleText[WYSIWYG.Type.BLOCKQUOTE] = btnIndent
        mapStyleText[WYSIWYG.Type.DIV] = btnInsertCode


        noteLibrary.setOnDecorationChangeListener(object : WYSIWYG.OnDecorationStateListener {
            override fun onStateChangeListener(text: String?, types: MutableList<WYSIWYG.Type>) {
                Log.e("onStateChangeListener", " $text $types")
                if (types!!.isNotEmpty()) {
                    for (type in types) {
                        if (type == WYSIWYG.Type.UNORDEREDLIST) {
                            types.remove(WYSIWYG.Type.ORDEREDLIST)
                        }
                    }
                    for (btn in mapStyleText) {
                        val type = btn.key
                        if (checkTypeChoose(type, types)) {
                            mapStyleText[type]!!.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.gray
                                )
                            )
                        } else
                            mapStyleText[type]!!.setBackgroundColor(Color.TRANSPARENT)
                    }
                } else {
                    for (btn in mapStyleText)
                        mapStyleText[btn.key]!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        })

        noteLibrary.setOnTextChangeListener(object : WYSIWYG.OnTextChangeListener {
            override fun onTextChange(text: String?) {
                Log.e("onTextChange", " $text ")
                contents = noteLibrary.html.toString()
            }
        })

        btnBack.setOnClickListener {
            val isChangeTitle =
                edtTitle.text.toString() != title && edtTitle.text.toString().isNotEmpty()

            //TODO hiển thị popup hỏi có lưu hay ko
            if (isChangeTitle || (contents != contentsOld))
                openDialogSave()
            else requireActivity().supportFragmentManager.popBackStack()
//            Log.e("contents", "$contents")
//            Log.e("contentsOld", "$contentsOld")
        }

        btnSave.setOnClickListener {
            saveNote()
            requireActivity().supportFragmentManager.popBackStack()
        }

        val menuItems: MutableList<MenuItem> = ArrayList()
        menuItems.add(MenuItem(0, R.drawable.ic_see, R.color.black, getString(R.string.pin)))
        menuItems.add(MenuItem(1, R.drawable.ic_screen, R.color.black, getString(R.string.share)))
        menuItems.add(MenuItem(2, R.drawable.ic_delete, R.color.black, getString(R.string.delete)))
        menuItems.add(MenuItem(3, R.drawable.ic_setting_black, R.color.black, getString(R.string.remind)))
        menuItems.add(MenuItem(4, R.drawable.ic_faq, R.color.black, getString(R.string.delete)))

        btnMenuRightList.setOnClickListener {
            val dialogMenuLeft = newInstance<DialogMenuLeft>("menuItems" to toJson(menuItems))
            dialogMenuLeft.show(childFragmentManager, "dialogMenuLeft")
            dialogMenuLeft.setListene(this)
        }

        btnTheme.setOnClickListener {
            val bottomSheetThemeFragment = BottomSheetThemeFragment()
            bottomSheetThemeFragment.show(childFragmentManager, "BottomSheetThemeFragment")
            bottomSheetThemeFragment.setListene(this)
        }

        btnTxtColor.setOnClickListener {
            val bottomSheetPickColorFragment =
                BottomSheetPickColorFragment()
            bottomSheetPickColorFragment.show(childFragmentManager, "BottomSheetPickColorFragment")
            bottomSheetPickColorFragment.setListene(this)
        }

        btnBold.setOnClickListener {
            noteLibrary.setBold()
        }

        btnItalic.setOnClickListener {
            noteLibrary.setItalic()
        }

        btnUnderline.setOnClickListener {
            noteLibrary.setUnderline()
        }

        btnUndo.setOnClickListener { noteLibrary.undo() }

        btnRedo.setOnClickListener { noteLibrary.redo() }

//        btnSubscript.setOnClickListener { noteLibrary.setSubscript() }
//
//        btnSuperScript.setOnClickListener { noteLibrary.setSuperscript() }

        btnStrikeThrough.setOnClickListener { noteLibrary.setStrikeThrough() }

        btnHeading1.setOnClickListener {
            noteLibrary.setHeading(
                1
            )
        }

        btnHeading2.setOnClickListener {
            noteLibrary.setHeading(
                2
            )
        }

        btnHeading3.setOnClickListener {
            noteLibrary.setHeading(
                3
            )
        }

        btnHeading4.setOnClickListener {
            noteLibrary.setHeading(
                4
            )
        }

        btnHeading5.setOnClickListener {
            noteLibrary.setHeading(
                5
            )
        }

        btnHeading6.setOnClickListener {
            noteLibrary.setHeading(
                6
            )
        }

        btnBgColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                noteLibrary.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        btnIndent.setOnClickListener { noteLibrary.setIndent() }

        btnOutdent.setOnClickListener { noteLibrary.setOutdent() }

        btnAlignLeft.setOnClickListener { noteLibrary.setAlignLeft() }

        btnAlignCenter.setOnClickListener { noteLibrary.setAlignCenter() }

        btnAlignRight.setOnClickListener { noteLibrary.setAlignRight() }

        btnAlignJustify.setOnClickListener { noteLibrary.setAlignJustifyFull() }

        btnBlockQuote.setOnClickListener { noteLibrary.setBlockquote() }

        btnInsertBullets.setOnClickListener { noteLibrary.setBullets() }

        btnInsertNumbers.setOnClickListener { noteLibrary.setNumbers() }

//        btnInsertImage.setOnClickListener {
//            noteLibrary.insertImage(
//                "https://i.postimg.cc/JzL891Fm/maxresdefault.jpg",
//                "Night Sky"
//            )
//        }

//        btnInsertCheckBox.setOnClickListener { noteLibrary.insertTodo() }

        btnInsertCode.setOnClickListener { noteLibrary.setCode() }

//        btnChangeFontType.setOnClickListener { noteLibrary.setFontType("Times New Roman") }

    }

    private fun saveNote() {
        title = edtTitle.text.toString()

        if (itemCategory!!.id == 0) {
            itemCategory!!.id = Calendar.getInstance().timeInMillis.toInt()
        }

        itemCategory!!.idNote = itemCategory!!.id
        noteModel.id = itemCategory!!.idNote

        if (title.isNotEmpty()) {
            itemCategory!!.title = title
        } else {
            itemCategory!!.title = "Không có tiêu đề"
        }

        itemCategory!!.image = R.drawable.ic_note_color
        itemCategory!!.icon = 0
        itemCategory!!.color = R.color.yellow1
        itemCategory!!.colorButton = 0
        itemCategory!!.bin = true
        itemCategory!!.lock = true

        noteModel.title = itemCategory!!.title
        noteModel.content = contents

        DBManager.getInstance().addNote(noteModel)
        DBManager.getInstance().addCategory(itemCategory!!)

        KLog.json(toJson(noteModel))
        KLog.json(toJson(itemCategory!!))

//        KLog.json(toJson(DBManager.getInstance().noteItemAll))
//        KLog.json(toJson(DBManager.getInstance().categoryItemAll))
    }

    private fun openDialogSave() {
        val contentView = View.inflate(context, R.layout.dialog_save, null)
        val dialog = CFAlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setHeaderView(contentView)
            .create()

        val btnSave = contentView.findViewById<carbon.widget.TextView>(R.id.btnSave)
        val btnClose = contentView.findViewById<carbon.widget.TextView>(R.id.btnDismiss)

        btnSave.setOnClickListener {
            saveNote()
            dialog.dismiss()
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnClose.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            dialog.dismiss()
        }

        dialog.show()

    }


    override fun onMenuHome(menuItem: MenuItem) {
        when (menuItem.id) {
            0 -> {

            }

            1 -> {

            }

            2 -> {

            }

            3 -> {

            }

            4 -> {
                DBManager.getInstance().updateCategoryBinDelete(itemCategory!!, false)
            }
        }
    }

    override fun onImgBackGround(colorBar: Int, colorBackGround: Int) {
        noteModel.colorBar = colorBar
        noteModel.colorBackGround = colorBackGround
        linearMenuTop.setBackgroundColor(resources.getColor(colorBar))
        noteLibrary.setBackgroundColor(resources.getColor(colorBackGround))
        horizontalScrollView.setBackgroundColor(resources.getColor(colorBar))
    }

    override fun onColorText(colorText: Int) {
        noteModel.colorText = colorText
        noteLibrary.setTextColor(resources.getColor(colorText))
    }

}

