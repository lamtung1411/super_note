package com.newsoft.super_note.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.newsoft.nscustom.ext.context.checkHideKeyboardOnTouchScreen
import com.newsoft.nscustom.ext.context.hideSoftKeyboard
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


abstract class BaseFragment(@LayoutRes private val layoutRes: Int) : Fragment(layoutRes) {

    var dataResult: ((Int, Int, Intent) -> Unit)? = null
    var activity: RxAppCompatActivity? = null
    var savedInstanceState: Bundle? = null

    abstract fun onCreate()
    abstract fun onViewCreated(view: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity() as RxAppCompatActivity
        onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.savedInstanceState = savedInstanceState
        requireActivity().hideSoftKeyboard()
        requireActivity().checkHideKeyboardOnTouchScreen(view)
        onViewCreated(view)
    }

    //TODO trả về file data Intent
    fun setDataIntentResult(dataResult: ((Int, Int, Intent) -> Unit)? = null) {
        this.dataResult = dataResult
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        dataResult?.let { it.invoke(requestCode, resultCode, data!!) }
    }

}
