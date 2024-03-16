package com.newsoft.super_note.ui.home

import android.util.Log
import android.view.View
import com.newsoft.nscustom.ext.context.newInstance
import com.newsoft.nscustom.ext.context.switchFragmentBackStack
import com.newsoft.nscustom.ext.value.toJson
import com.newsoft.nscustom.ext.view.text
import com.newsoft.nscustom.view.recyclerview.RvLayoutManagerEnums
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.MenuItem
import com.newsoft.super_note.data.sqlite.DBManager
import com.newsoft.super_note.ui.base.BaseFragment
import com.newsoft.super_note.ui.home.adapter.CategoryAdapter
import com.newsoft.super_note.ui.menu.CallBackChangeMenu
import com.newsoft.super_note.ui.menu.DialogMenuLeft
import com.newsoft.super_note.ui.note.NoteFragment
import com.newsoft.super_note.ui.setting.SettingFragment
import com.newsoft.super_note.ui.task.TaskFragment
import com.newsoft.super_note.utils.container
import com.socks.library.KLog
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_note.edtTitle
import render.animations.Render
import java.util.Calendar


class HomeFragment : BaseFragment(R.layout.fragment_home), CallBackChangeMenu {

    private var categoryAdapter: CategoryAdapter? = null
    var listCategory = ArrayList<CategoryModel.Item>()
    val categoryModelDefault = ArrayList<CategoryModel.Item>()

    override fun onCreate() {

        categoryAdapter = CategoryAdapter()

        categoryModelDefault.add(
            CategoryModel.Item(
                1,
                0,
                "Add Txt",
                false,
                R.drawable.ic_note_color,
                R.drawable.ic_new_note,
                R.color.yellow1,
                R.color.yellow,
            )
        )

        categoryModelDefault.add(
            CategoryModel.Item(
                0,
                2,
                "Add Checklist",
                false,
                R.drawable.ic_list_color,
                R.drawable.ic_new_list,
                R.color.blue3,
                R.color.blue
            )
        )
    }

    override fun onViewCreated(view: View) {

        listCategory = DBManager.getInstance().getCategoryItemsIsBin(true)
        KLog.json(toJson(DBManager.getInstance().getCategoryItemsIsBin(true)))

        categoryAdapter?.apply {
            setRecyclerView(
                rvCategory,
                type = RvLayoutManagerEnums.LinearLayout_VERTICAL
            )

            if (listCategory.size == 0) {
                setItems(categoryModelDefault, 0)
            } else {
                setItems(listCategory, 0)
            }

            setLoadData(edtTitle) {
                requireActivity().runOnUiThread {
                    val kw = edtTitle.text()
                    if (kw.isNotEmpty()) {
                        listCategory = DBManager.getInstance().getCategoryItemsSearch(kw)
                        setItems(listCategory, 0)
                    }
                }
            }

            setOnAdapterListener { id, item, pos ->
                when (id) {
                    1 -> {
                        if (item != null) {
                            if (item.idNote != 0) {
                                switchFragmentBackStack(
                                    container,
                                    newInstance<NoteFragment>("item" to toJson(item))
                                )
                            } else if (item.idTask != 0) {
                                switchFragmentBackStack(
                                    container,
                                    newInstance<TaskFragment>("item" to toJson(item))
                                )
                            }
                        }
                    }

                    2 -> {
                        item!!.pin = !item.pin

                        if (item!!.timeCountPin == 0L) {
                            item.timeCountPin = Calendar.getInstance().timeInMillis
                        } else if (item.timeCountPin != 0L) {
                            item.timeCountPin = 0L
                            Log.e("timeCountPin", "${item.timeCountPin}")
                        }
                        DBManager.getInstance().updateCategory(item)

                        KLog.json(toJson(items))

                        listCategory = DBManager.getInstance().getCategoryItemsIsBin(true)
                        setItems(listCategory, 0)
                        KLog.json(toJson(listCategory))
                    }

                    3 -> {

                    }

                    4 -> {
                        DBManager.getInstance().updateCategoryBinDelete(item!!, false)
                    }
                }
            }
        }

        val menuItems: MutableList<MenuItem> = ArrayList()
        menuItems.add(MenuItem(0, R.drawable.ic_see, R.color.black,  getString(R.string.view)))
        menuItems.add(MenuItem(1, R.drawable.ic_screen, R.color.black, getString(R.string.theme)))
        menuItems.add(MenuItem(2, R.drawable.ic_delete, R.color.black, getString(R.string.recycle_bin)))
        menuItems.add(MenuItem(3, R.drawable.ic_setting_black, R.color.black, getString(R.string.setting)))
        menuItems.add(MenuItem(4, R.drawable.ic_faq, R.color.black, getString(R.string.faq)))
        menuItems.add(MenuItem(5, R.drawable.ic_add, R.color.black, getString(R.string.more_app)))
        menuItems.add(MenuItem(6, R.drawable.ic_premie_black, R.color.black, getString(R.string.update_vip)))

        btnMenuRight.setOnClickListener {
            val dialogMenuLeft = newInstance<DialogMenuLeft>("menuItems" to toJson(menuItems))
            dialogMenuLeft.show(childFragmentManager, "dialogMenuLeft")
            dialogMenuLeft.setListene(this)
        }

        btnSetting.setOnClickListener {
            switchFragmentBackStack(
                container,
                SettingFragment()
            )
        }

        btnCloud.setOnClickListener {
            switchFragmentBackStack(
                container,
                LogOutGoogleFragment()
            )
        }

        btnNewNote.setOnClickListener {
            switchFragmentBackStack(
                container,
                NoteFragment()
            )
        }

        btnNewList.setOnClickListener {
            switchFragmentBackStack(
                container,
                TaskFragment()
            )
        }
    }

    override fun onMenuHome(menuItem: MenuItem) {
        when (menuItem.id) {
            0 -> {

            }

            1 -> {

            }

            2 -> {
                switchFragmentBackStack(
                    container,
                    BinFragment()
                )
            }

            3 -> {

            }

            4 -> {

            }

            5 -> {

            }

            6 -> {

            }

        }
    }

}



