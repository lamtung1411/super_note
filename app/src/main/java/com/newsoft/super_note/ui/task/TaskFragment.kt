package com.newsoft.super_note.ui.task

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsoft.nscustom.ext.context.getDataExtras
import com.newsoft.nscustom.ext.context.newInstance
import com.newsoft.nscustom.ext.value.fromJson
import com.newsoft.nscustom.ext.value.toJson
import com.newsoft.nscustom.view.cfalertdialog.CFAlertDialog
import com.newsoft.nscustom.view.recyclerview.RvLayoutManagerEnums
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.MenuItem
import com.newsoft.super_note.data.model.TaskModel
import com.newsoft.super_note.data.sqlite.DBManager
import com.newsoft.super_note.ui.base.BaseFragment
import com.newsoft.super_note.ui.menu.CallBackChangeMenu
import com.newsoft.super_note.ui.menu.DialogMenuLeft
import com.newsoft.super_note.ui.menu.adapter.MenuLeftAdapter
import com.newsoft.super_note.ui.task.adapter.TaskAdapter
import com.newsoft.super_note.utils.setFocus
import com.socks.library.KLog
import kotlinx.android.synthetic.main.fragment_note.linearMenuTop
import kotlinx.android.synthetic.main.fragment_task.*

import render.animations.Render
import java.util.*


class TaskFragment : BaseFragment(R.layout.fragment_task),
    CallBackChangeMenu, CallBackChangeBackGroundList {

    private var menuLeftAdapter: MenuLeftAdapter? = null
    private var taskAdapter: TaskAdapter? = null
    private var render: Render? = null
    var itemCategory: CategoryModel.Item? = null
    var taskModels = ArrayList<TaskModel.Item>()
    var taskOldModels = ArrayList<TaskModel.Item>()
    var taskModel = TaskModel()

    var title = " "
    var getDataTask: ArrayList<TaskModel.Item>? = null

    override fun onCreate() {
        menuLeftAdapter = MenuLeftAdapter(requireContext(), this)
        taskAdapter = TaskAdapter()
        render = Render(requireContext())
        itemCategory = CategoryModel.Item()

        getDataExtras<String>("item", "").let {
            val itemCategory = fromJson(it, CategoryModel.Item::class.java)
            if (itemCategory != null)
                this.itemCategory = itemCategory
        }
    }

    override fun onViewCreated(view: View) {

        //TODO lấy dữ liệu taskModel trong DBManager.getInstance().taskItemAll từ idTask của itemCategory
        itemCategory?.let { itemCategory ->
            taskModel = DBManager.getInstance().getTaskItem(itemCategory.idTask)
            taskModel.items?.let { taskModel ->
                taskModels.addAll(taskModel)
                taskOldModels.addAll(taskModel)
            }

            if (itemCategory!!.idTask != 2) {
                title = itemCategory.title
                edtTitle.setText(title)
            }
        }

        if (taskModel.colorBar != 0 || taskModel.colorBackGround != 0) {
            taskModel.let { taskModel ->
                linearMenuTop.setBackgroundColor(resources.getColor(taskModel.colorBar))
                contraintBackGroundList.setBackgroundColor(resources.getColor(taskModel.colorBackGround))
            }
        }

        val menuItems: MutableList<MenuItem> = ArrayList()
        menuItems.add(MenuItem(0, R.drawable.ic_see, R.color.black, "Pin"))
        menuItems.add(MenuItem(1, R.drawable.ic_screen, R.color.black, "Share"))
        menuItems.add(MenuItem(2, R.drawable.ic_delete, R.color.black, "Theme"))
        menuItems.add(MenuItem(3, R.drawable.ic_setting_black, R.color.black, "Remind"))
        menuItems.add(MenuItem(4, R.drawable.ic_faq, R.color.black, "Delete"))

        btnMenuRightList.setOnClickListener {
            val dialogMenuLeft = newInstance<DialogMenuLeft>("menuItems" to toJson(menuItems))
            dialogMenuLeft.show(childFragmentManager, "dialogMenuLeft")
            dialogMenuLeft.setListene(this)
        }

        if (taskModels.size == 0)
            taskModels.add(
                TaskModel.Item(
                    Calendar.getInstance().timeInMillis.toInt(),
                    false,
                    ""
                )
            )

        taskAdapter?.apply {
            setRecyclerView(rvTask, type = RvLayoutManagerEnums.LinearLayout_VERTICAL)
            setItems(taskModels, 0)
            setOnAdapterListener { id, item, pos ->
                when (id) {
                    1 -> {
                        getDataFromEditTextByAdapter()

                        if (itemCount == 1) {
                            taskAdapter!!.items[0]!!.content = ""
                            taskAdapter!!.items[0]!!.tick = false
                        } else {
                            taskModels.removeAt(pos)
                        }
                        notifyDataSetChanged()
//                        KLog.json(toJson(taskModels))
                    }

                    2 -> {
                        //TODO thêm mới 1 task thì lưu lại dữ liệu cũ
                        getDataFromEditTextByAdapter()

                        val itemAddNew = TaskModel.Item(
                            Calendar.getInstance().timeInMillis.toInt(),
                            false,
                            ""
                        )

                        taskAdapter?.addItem(itemAddNew)

                        Handler(Looper.myLooper()!!).postDelayed({
                            val viewHolder =
                                rvTask.findViewHolderForAdapterPosition(pos + 1) as TaskAdapter.AdapterHolder
                            viewHolder.edtContent.setFocus(requireContext())
                            rvTask.scrollToPosition(itemCount - 1)
                        }, 200L)
                    }
                }
            }
        }

        btnSave.setOnClickListener {
            saveTask()
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnBack.setOnClickListener {
            val isChangeTitle =
                edtTitle.text.toString() != title && edtTitle.text.toString().isNotEmpty()
            val getDataTaskNew = getDataFromEditTextByAdapter()

            var isChangeDataTask = false

            if (taskOldModels.size > 1) {
                isChangeDataTask =
                    toJson(getDataTaskNew) != toJson(taskOldModels)
            } else {
                isChangeDataTask =
                    getDataTaskNew[0].content.isNotEmpty() && taskAdapter!!.itemCount > 1
            }
//            Log.e("taskModels", " ${toJson(taskOldModels)}")
//            Log.e("getDataTaskNew", " ${toJson(getDataTaskNew)}")
//            Log.e("isChangeDataTask", " $isChangeDataTask")
//            Log.e("taskModels", " ${taskOldModels.size}")

            //TODO hiển thị popup hỏi có lưu hay ko
            if (isChangeTitle || (isChangeDataTask)) openDialogSave() else requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun saveTask() {

        title = edtTitle.text.toString()
        getDataTask = getDataFromEditTextByAdapter()
        taskModels.clear()

        if (itemCategory!!.id == 0) {
            itemCategory!!.id = Calendar.getInstance().timeInMillis.toInt()
        }

        itemCategory!!.idTask = itemCategory!!.id
        taskModel.id = itemCategory!!.idTask

        if (title.isNotEmpty()) {
            itemCategory!!.title = title
        } else {
            itemCategory!!.title = "Không có tiêu đề"
        }

        itemCategory!!.image = R.drawable.ic_list_color
        itemCategory!!.color = R.color.blue3
        itemCategory!!.icon = 0
        itemCategory!!.colorButton = 0
        itemCategory!!.bin = true
        itemCategory!!.lock = true

        taskModel.title = itemCategory!!.title

        taskModels.addAll(getDataTask!!)
        taskModel.items = taskModels

        DBManager.getInstance().addCategory(itemCategory!!)
        KLog.json(toJson(DBManager.getInstance().categoryItemAll))

        DBManager.getInstance().addTask(taskModel)
        KLog.json(toJson(DBManager.getInstance().taskItemAll))

        for (item in taskModels) {
            item.idTask = taskModel.id
            DBManager.getInstance().addTaskDetail(item)
            KLog.json(toJson(DBManager.getInstance().taskDetailItemAll))
        }
        KLog.json(toJson(taskModels))

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
            dialog.dismiss()
            saveTask()
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnClose.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            dialog.dismiss()
        }

        dialog.show()
    }

    //TODO Lấy tất cả dữ liệu trong danh sách adapter
    private fun getDataFromEditTextByAdapter(): ArrayList<TaskModel.Item> {
        val items = ArrayList<TaskModel.Item>()

        val itemCountVisibleFirstCompletelyVisible =
            (rvTask.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

        var itemCountVisibleLastCompletelyVisible =
            (rvTask.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

        var itemCountVisibleLastVisibleItemPosition =
            (rvTask.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

//        Log.e("size", "${taskAdapter!!.itemCount}")
//        Log.e("itemCountVisibleFirstCompletelyVisible", "$itemCountVisibleFirstCompletelyVisible")
//        Log.e("itemCountVisibleLastCompletelyVisible", "$itemCountVisibleLastCompletelyVisible")
//        Log.e("itemCountVisibleLastVisibleItemPosition", "$itemCountVisibleLastVisibleItemPosition")


        for (i in itemCountVisibleFirstCompletelyVisible until itemCountVisibleLastCompletelyVisible + 1) {
            val viewHolder = rvTask.findViewHolderForAdapterPosition(i) as TaskAdapter.AdapterHolder
            val content = viewHolder.edtContent.text.toString()
            val tick = viewHolder.checkboxList.isChecked

            taskAdapter!!.items[i]!!.tick = tick
            taskAdapter!!.items[i]!!.content = content

            items.add(taskAdapter!!.items[i]!!)
        }
        KLog.json(toJson(items))
        return items

    }


    override fun onImgBackGround(colorBar: Int, colorBackGround: Int) {
        taskModel.colorBar = colorBar
        taskModel.colorBackGround = colorBackGround
        linearMenuTop.setBackgroundColor(resources.getColor(colorBar))
        contraintBackGroundList.setBackgroundColor(resources.getColor(colorBackGround))
    }

    override fun onMenuHome(menuItem: MenuItem) {
        when (menuItem.id) {
            0 -> {

            }

            1 -> {

            }

            2 -> {
                val bottomSheetThemeFragment = BottomSheetThemeFragment()
                bottomSheetThemeFragment.show(childFragmentManager, "BottomSheetThemeFragment")
                bottomSheetThemeFragment.setListene(this)
            }

            3 -> {

            }

            4 -> {
                DBManager.getInstance().updateCategoryBinDelete(itemCategory!!, false)
            }
        }
    }

}

