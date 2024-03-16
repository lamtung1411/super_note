package com.newsoft.super_note.ui.main


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.newsoft.super_note.R
import com.newsoft.super_note.ui.base.BaseActivity
//import com.newsoft.super_note.ui.login.LoginActivity

import com.newsoft.nscustom.ext.context.*
import com.newsoft.super_note.R.color.black
import com.newsoft.super_note.R.color.gray8
import com.newsoft.super_note.ui.base.AppLayerManager
import com.newsoft.super_note.data.model.base.UploadFileModel
import com.newsoft.super_note.data.rest.Presenter
import com.newsoft.super_note.data.rest.exception.LoadingType
import com.newsoft.super_note.data.rest.exception.subscribeUntilDestroy
import com.newsoft.super_note.ui.chat.ChatFragment
import com.newsoft.super_note.ui.home.HomeFragment

import com.newsoft.super_note.utils.*
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.activity_main.*

import kotlin.system.exitProcess


class MainActivity : BaseActivity(R.layout.activity_main) {

    //https://github.com/dinuscxj/RecyclerRefreshLayout

    var doubleBackToExitPressedOnce = false
    var homeFragment: HomeFragment? = null
    var lastPositionTabSelected = 0

    //dp = px * 160 / dpi

    override fun onCreate() {
        isAutoCheckHiderKeyboard=false
        AppLayerManager.getInstance().isOpenApp = true

//
        homeFragment = HomeFragment()
//
        switchFragment(container,homeFragment!!)
//        initMenuLeft()
//        initBottomNavigation()
        onNewIntent(intent)

//        DBManager.getInstance().deleteTable(DBManager.TABLE_NOTE)
//        DBManager.getInstance().deleteTable(DBManager.TABLE_TASK)
//        DBManager.getInstance().deleteTable(DBManager.TABLE_CATEGORY)

//        KLog.json(toJson(DBManager.getInstance().taskDetailItemAll))
//        KLog.json(toJson(DBManager.getInstance().taskItemAll))
    }


//    private fun checkVersionApp() {
//        presenter().onCheckVersion()
//            .subscribeUntilDestroyNotCheckError(this, LoadingType.NOT) {
//                onNext {
//                    if (it.role != "android")
//                        return@onNext
//
//                    val versionApp = packageManager.getPackageInfo(packageName, 0)
//                        .versionName.replace(".", "").toInt()
//
//                    val versionApi = it.version.replace(".", "").toInt()
//                    if (versionApi > versionApp) {
//                        startActivity(Intent(this@MainActivity, UpdateAppActivity::class.java))
//                        finish()
//                    }
//                }
//            }
//    }

//    private fun initBottomNavigation() {
//
//        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
//        bottomNavigation.defaultBackgroundColor =
//            ContextCompat.getColor(this, R.color.white)
//        bottomNavigation.accentColor =
//            ContextCompat.getColor(this, R.color.blueactionbar)
//        bottomNavigation.inactiveColor = ContextCompat.getColor(this, gray8)
//
//        val tab1 = AHBottomNavigationItem(
//            R.string.TABCHAT,
//            R.drawable.ic_messages, gray8
//        )
//        val tab2 =
//            AHBottomNavigationItem(
//                R.string.TABNOTE,
//                R.drawable.ic_note_bottom, gray8
//            )
////        val tab3 = AHBottomNavigationItem(
////            R.string.TAB3,
////            R.drawable.ic_person, R.color.black
////        )
//
//        bottomNavigation.addItem(tab1)
//        bottomNavigation.addItem(tab2)
////        bottomNavigation.addItem(tab3)
//        bottomNavigation.setOnTabSelectedListener { position: Int, wasSelected: Boolean ->
//            when (bottomNavigation.getItem(position)) {
//                tab1 -> {
//                    switchFragment(container, ChatFragment())
//                }
//                tab2 -> {
//                    switchFragment(container, HomeFragment())
//                }
////                tab3 -> {
////                    switchFragment(container, ProfileFragment())
////                }
//            }
//            this.lastPositionTabSelected = position
//            true
//        }
//        setCurrentItem(lastPositionTabSelected)
//    }
//
//    private fun setCurrentItem(currentItem: Int) {
//        bottomNavigation.currentItem = currentItem
//    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 0) {
            if (doubleBackToExitPressedOnce) {
                finish()
                exitProcess(0)
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Nhấn back 2 lần để thoát !", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                kotlin.run {
                    doubleBackToExitPressedOnce = false
                }
            }, 3000)
        } else
            supportFragmentManager.popBackStack()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == 10) {
//            homeFragment?.let {
//                it.onListConnect()
//            }
//        }
//    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val uriString = Matisse.obtainPathResult(data)[0]
            var uri = Matisse.obtainResult(data)[0]

            onDialogLoadingShadow()
            onUploadFile(uri)
        }
    }

    private fun onUploadFile(uri: Uri) {
        presenter().onUploadFile(
            this,
            uri,
            object : Presenter.UploadFileListener {
                override fun onResponse(uploadFileModel: UploadFileModel) {
//                    image = uploadFileModel.fileStoreURL
                    presenter().onUpdateAvatar(uploadFileModel.fileStoreURL)
                        .subscribeUntilDestroy(
                            this@MainActivity,
                            LoadingType.NOT
                        ) {
                            onNext {
                                onDismissDialogLoading()
                                AppLayerManager.getInstance().profileUserModel = it

                                Toast.makeText(
                                    this@MainActivity,
                                    "Tải lên ảnh đại diện thành công !",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            })
    }


}