package com.newsoft.super_note.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsoft.nscustom.ext.context.newInstance
import com.newsoft.nscustom.ext.context.switchFragmentBackStack
import com.newsoft.nscustom.ext.value.toJson

import com.newsoft.nscustom.view.recyclerview.RvLayoutManagerEnums
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.MenuItem
import com.newsoft.super_note.data.sqlite.DBManager
import com.newsoft.super_note.ui.bin.NoteBinFragment
import com.newsoft.super_note.ui.bin.TaskBinFragment
import com.newsoft.super_note.ui.menu.CallBackChangeMenu
import com.newsoft.super_note.ui.menu.DialogMenuLeft
import com.newsoft.super_note.ui.menu.adapter.MenuLeftAdapter
import com.newsoft.super_note.ui.notification.adapter.BinAdapter
import com.newsoft.super_note.utils.container
import com.socks.library.KLog
import kotlinx.android.synthetic.main.fragment_bin.btnBack
import kotlinx.android.synthetic.main.fragment_bin.rvBin

import kotlinx.android.synthetic.main.fragment_home.*
import render.animations.Render
import render.animations.Slide


class BinFragment : Fragment(),
    CallBackChangeMenu {

    private var binAdapter: BinAdapter? = null
    private var menuLeftAdapter: MenuLeftAdapter? = null
    private var render: Render? = null
    var listBin = ArrayList<CategoryModel.Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuLeftAdapter = MenuLeftAdapter(requireContext(), this)

        binAdapter = BinAdapter()

        render = Render(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bin, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listBin = DBManager.getInstance().getCategoryItemsIsBin(false)
        KLog.json(toJson(DBManager.getInstance().getCategoryItemsIsBin(false)))


        binAdapter?.apply {
            setRecyclerView(
                rvBin,
                type = RvLayoutManagerEnums.LinearLayout_VERTICAL
            )
            setItems(listBin, 0)
            setOnAdapterListener { id, item, pos ->
                when (id) {
                    1 -> {
                        if (item!!.idTask != 0) {
                            switchFragmentBackStack(
                                container,
                                newInstance<TaskBinFragment>("item" to toJson(item))
                            )
                        } else {
                            switchFragmentBackStack(
                                container,
                                newInstance<NoteBinFragment>("item" to toJson(item))
                            )
                        }
                    }

                    2 -> {
                        DBManager.getInstance().updateCategoryBinDelete(item!!,true)
                    }

                    3 -> {
                        DBManager.getInstance().delete(DBManager.TABLE_CATEGORY, item!!.id)
                    }
                }
            }
        }

        val menuItems: MutableList<MenuItem> = ArrayList()
        menuItems.add(MenuItem(0, R.drawable.ic_see, R.color.black, getString(R.string.view)))
        menuItems.add(MenuItem(1, R.drawable.ic_screen, R.color.black, getString(R.string.restore_all)))
        menuItems.add(MenuItem(2, R.drawable.ic_delete, R.color.black, getString(R.string.delete_all)))

        btnMenuRight.setOnClickListener {
            val dialogMenuLeft = newInstance<DialogMenuLeft>("menuItems" to toJson(menuItems))
            dialogMenuLeft.show(childFragmentManager, "dialogMenuLeft")
            dialogMenuLeft.setListene(this)
        }

        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    override fun onMenuHome(menuItem: MenuItem) {
        when (menuItem.id) {
            0 -> {
//                onDisableMenuRight()
            }

            1 -> {
                for (item in listBin){
                    DBManager.getInstance().updateCategoryBinDelete(item,true)
                }
                listBin.clear()
                binAdapter?.notifyDataSetChanged()
            }

            2 -> {
                for (item in listBin){
                    DBManager.getInstance().deleteBinItems(DBManager.TABLE_CATEGORY,item.id)
                }
                listBin.clear()
                binAdapter?.notifyDataSetChanged()
            }
        }
    }

}

