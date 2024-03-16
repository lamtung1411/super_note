package com.newsoft.super_note.ui.bin

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsoft.nscustom.ext.context.getDataExtras
import com.newsoft.nscustom.ext.value.fromJson
import com.newsoft.nscustom.ext.value.toJson
import com.newsoft.nscustom.view.cfalertdialog.CFAlertDialog
import com.newsoft.nscustom.view.recyclerview.RvLayoutManagerEnums
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.MenuItem
import com.newsoft.super_note.data.model.TaskModel
import com.newsoft.super_note.data.sqlite.DBManager
import com.newsoft.super_note.ui.bin.adapter.TaskBinAdapter
import com.newsoft.super_note.ui.menu.adapter.MenuLeftAdapter
import com.newsoft.super_note.ui.task.adapter.TaskAdapter
import com.newsoft.super_note.utils.setFocus
import com.socks.library.KLog
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_task_bin.btnDeleteBin
import kotlinx.android.synthetic.main.fragment_task_bin.btnRestore
import kotlinx.android.synthetic.main.fragment_task_bin.viewShade

import java.util.*


class TaskBinFragment : Fragment() {

    private var taskBinAdapter: TaskBinAdapter? = null

    var itemCategory: CategoryModel.Item? = null
    var taskModels = ArrayList<TaskModel.Item>()
    var taskModel = TaskModel()
    var title = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taskBinAdapter = TaskBinAdapter()

        getDataExtras<String>("item", "").let {
            itemCategory = fromJson(it, CategoryModel.Item::class.java)
            KLog.json(toJson(itemCategory))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_bin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO lấy dữ liệu taskModel trong DBManager.getInstance().taskItemAll từ idTask của itemCategory
        itemCategory?.let { itemCategory ->
            val taskModelDb = DBManager.getInstance().getTaskItem(itemCategory.idTask)
            KLog.json(toJson(taskModelDb))
            taskModel = taskModelDb
            taskModelDb.items?.let { taskModelDb ->
                taskModels.addAll(taskModelDb)
            }
        }

        itemCategory?.let {
            if (itemCategory!!.idTask != 2) {
                title = it.title
                edtTitle.setText(title)
            }
        }

        edtTitle.setFocusable(false)
        edtTitle.setEnabled(false)
        edtTitle.setCursorVisible(false)
        edtTitle.setKeyListener(null)

        taskBinAdapter?.apply {
            setRecyclerView(rvTask, type = RvLayoutManagerEnums.LinearLayout_VERTICAL)
            setItems(taskModels, 0)
        }

        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        viewShade.setOnClickListener {
            openDialogRestore()
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
        val contentView = View.inflate(context, R.layout.dialog_delete, null)
        val dialog = CFAlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setHeaderView(contentView)
            .create()
        val tvInformation = contentView.findViewById<TextView>(R.id.tvInformation)
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



}

