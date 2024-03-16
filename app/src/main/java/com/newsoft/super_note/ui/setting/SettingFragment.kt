package com.newsoft.super_note.ui.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newsoft.nscustom.ext.context.switchFragmentBackStack

import com.newsoft.nscustom.view.recyclerview.RvLayoutManagerEnums
import com.newsoft.super_note.R
import com.newsoft.super_note.data.model.SettingModel
import com.newsoft.super_note.ui.base.BaseFragment
import com.newsoft.super_note.ui.home.BinFragment
import com.newsoft.super_note.ui.notification.adapter.SettingAdapter
import com.newsoft.super_note.utils.container

import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : BaseFragment(R.layout.fragment_setting) {

    private var settingAdapter: SettingAdapter? = null

    override fun onCreate() {

        settingAdapter = SettingAdapter()
        settingAdapter?.setOnAdapterListener { id, item, pos ->
            Log.e("setOnAdapterListener", " ")
            if (item != null) {
                if (item!!.id == "0") {
                    switchFragmentBackStack(
                        container,
                        BinFragment())
                } else {
//                    switchFragmentBackStack(
//                        container,
//                        ListFragment()
//                    )

                }
            }
        }
    }

    override fun onViewCreated(view: View) {

        settingAdapter?.setRecyclerView(rvSetting, type = RvLayoutManagerEnums.LinearLayout_VERTICAL)

        val settingModel = ArrayList<SettingModel.Item>()
        settingModel.add(
            SettingModel.Item(
                "0",
                getString(R.string.recycle_bin),
                R.drawable.ic_note_color
            )
        )
        settingModel.add(
            SettingModel.Item(
                "1",
                getString(R.string.synchronized_account),
                R.drawable.ic_list_color

            )
        )
        settingModel.add(
            SettingModel.Item(
                "2",
                getString(R.string.share_app),
                R.drawable.ic_list_color

            )
        )
        settingModel.add(
            SettingModel.Item(
                "3",
                getString(R.string.check_for_update),
                R.drawable.ic_list_color

            )
        )
        settingModel.add(
            SettingModel.Item(
                "4",
                getString(R.string.privacy_policy),
                R.drawable.ic_list_color

            )
        )
        settingModel.add(
            SettingModel.Item(
                "5",
                getString(R.string.update_vip),
                R.drawable.ic_list_color

            )
        )

        settingAdapter?.setItems(settingModel, 0)

        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

}

