package com.newsoft.super_note.ui.login

import android.content.Intent
import android.view.WindowManager
import com.newsoft.super_note.data.rest.exception.*
import com.newsoft.super_note.ui.base.AppLayerManager
import com.newsoft.super_note.ui.base.BaseActivity
import com.newsoft.super_note.ui.main.MainActivity
import com.newsoft.super_note.utils.presenter
import com.newsoft.nscustom.ext.context.startActivityExtFinish

class StartActivity : BaseActivity(0) {
    var type = ""

    override fun onCreate( ) {
        getTokenFirebase()
        onNewIntent(intent)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val attrib = window.attributes
        attrib.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//        onCheckToken()
        startActivityExtFinish<MainActivity>(0,"type" to type)
    }

//    private fun onCheckToken() {
//        presenter().onCheckToken()
//            .subscribeUntilDestroyNotCheckError(this, LoadingType.NOT) {
//                onNext {
//                    AppLayerManager.getInstance().profileUserModel = it
//                    startActivityExtFinish<MainActivity>(0,"type" to type)
//                }
//                onError {
//                    startActivityExtFinish<LoginActivity>()
//                }
//            }
//    }
//
//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        intent!!.getStringExtra("type")?.let {
//            type = it
//        }


    }


//}