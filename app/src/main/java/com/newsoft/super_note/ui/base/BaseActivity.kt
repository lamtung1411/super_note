package com.newsoft.super_note.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.newsoft.nscustom.ext.context.hideSoftKeyboard
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


abstract class BaseActivity(@LayoutRes private val layoutRes: Int) : RxAppCompatActivity() {

    var dataResult: ((Int, Int, Intent?) -> Unit)? = null
    var savedInstanceState: Bundle? = null
    var isAutoCheckHiderKeyboard=true
    abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        if (layoutRes != 0) setContentView(layoutRes)
        hideSoftKeyboard()
        onCreate()
    }

    /**
     * check hide keyboard on touch screen
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null&&isAutoCheckHiderKeyboard) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun getTokenFirebase() {
        if (AppLayerManager.getInstance().tokenFirebase.isEmpty())
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result

                    // Log and toast
                    Log.e("TokenFirebase", token)
                    AppLayerManager.getInstance().tokenFirebase = token
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        hideSoftKeyboard()
    }

    //TODO trả về file data Intent
    fun setDataIntentResult(dataResult: ((Int, Int, Intent?) -> Unit)? = null) {
        this.dataResult = dataResult
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            dataResult?.let { it.invoke(requestCode, resultCode, data) }
            for (fragment in supportFragmentManager.fragments)
                fragment.onActivityResult(requestCode, resultCode, data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}