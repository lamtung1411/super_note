package com.newsoft.super_note.utils

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.newsoft.super_note.R
import com.hedgehog.ratingbar.RatingBar
import com.newsoft.nscustom.constants.ResultCode
import com.newsoft.nscustom.ext.context.startActivityExt
import com.newsoft.super_note.data.rest.exception.LoadingType
import com.newsoft.super_note.data.rest.exception.subscribeUntilDestroy
import kotlinx.android.synthetic.main.fragment_task.view.*
//import com.newsoft.super_note.ui.login.LoginActivity

import java.util.*

class AlertDialogType {

    var icon: Int = 0
    var btnLeftBg: Int = 0
    var btnRightBg: Int = 0
    var contentBtnRight: String = ""
    var contentBtnLeft: String = ""
    var codeResponse: Int = 0
    var title: String = ""
    var content: String = ""
    var typeBtn = BtnDialogNoticeEnum.NO_BTN
    var listener: DialogNoticeListen? = null
    var isSuccess = false
    var isCancelable = true
    var isShowBtnClose = true

    /**
     * Show dialog msg
     */
    constructor(content: String) {
        this.content = content
    }

    /**
     * Show dialog response msg
     */
    constructor(content: String, codeResponse: Int) {
        this.codeResponse = codeResponse
        this.content = content
    }

    /**
     * Show dialog response success
     */
    constructor(content: String, codeResponse: Int, listener: DialogNoticeListen?) {
        this.content = content
        this.codeResponse = codeResponse
        this.listener = listener
        isSuccess = true
    }

    /**
     * Show dialog all btn
     */
    constructor(content: String, listener: DialogNoticeListen?) {
        this.content = content
        this.listener = listener
        typeBtn = BtnDialogNoticeEnum.ALL_BTN
    }

    /**
     * Show dialog all btn
     */
    constructor(content: String, isCancelable: Boolean, listener: DialogNoticeListen?) {
        this.content = content
        this.isCancelable = isCancelable
        this.listener = listener
        typeBtn = BtnDialogNoticeEnum.ALL_BTN
    }

    /**
     * Show dialog all btn
     */
    constructor(content: String, typeBtn: BtnDialogNoticeEnum, listener: DialogNoticeListen?) {
        this.content = content
        this.listener = listener
        this.typeBtn = typeBtn
    }

    constructor(
        title: String,
        content: String,
        typeBtn: BtnDialogNoticeEnum,
        listener: DialogNoticeListen?
    ) {
        this.title = title
        this.content = content
        this.listener = listener
        this.typeBtn = typeBtn
    }

    /**
     * Show dialog order success
     */
    constructor(
        content: String,
        contentBtnLeft: String,
        isSuccess: Boolean,
        listener: DialogNoticeListen?
    ) {
        this.content = content
        this.listener = listener
        this.contentBtnLeft = contentBtnLeft
        typeBtn = BtnDialogNoticeEnum.BTN_LEFT
        this.isSuccess = isSuccess
    }

    /**
     * Show dialog order success
     */
    constructor(
        content: String,
        contentBtnLeft: String,
        btnLeftBg: Int,
        isSuccess: Boolean,
        listener: DialogNoticeListen?
    ) {
        this.content = content
        this.listener = listener
        this.btnLeftBg = btnLeftBg
        this.contentBtnLeft = contentBtnLeft
        typeBtn = BtnDialogNoticeEnum.BTN_LEFT
        this.isSuccess = isSuccess
    }

}

/**
 * Show dialog btn notice
 */

enum class BtnDialogNoticeEnum {
    BTN_RIGHT,
    BTN_LEFT,
    ALL_BTN,
    NO_BTN
}

fun Activity.showAlertDialog(alertDialogType: AlertDialogType) {
    val dialog = Dialog(this)
    dialog.setCancelable(alertDialogType.isCancelable)
    var eventDismiss = BtnDialogNoticeEnum.NO_BTN

    dialog.apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_notice)

        val imageIcon = findViewById<ImageView>(R.id.imageIcon)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvContent = findViewById<TextView>(R.id.tvContent)
        val btnLeft = findViewById<TextView>(R.id.btnLeft)
        val view = findViewById<View>(R.id.view)
        val btnRight = findViewById<TextView>(R.id.btnRight)
        val viewBtn = findViewById<LinearLayout>(R.id.viewBtn)
        val btnClose = findViewById<ImageView>(R.id.btnClose)

        when (alertDialogType.typeBtn) {
            BtnDialogNoticeEnum.NO_BTN -> {
                btnLeft.visibility = View.GONE
                btnRight.visibility = View.GONE
                view.visibility = View.GONE
                viewBtn.visibility = View.GONE
                btnClose.visibility = View.VISIBLE
            }
            BtnDialogNoticeEnum.BTN_RIGHT -> {
                btnRight.visibility = View.VISIBLE
                btnLeft.visibility = View.GONE
                view.visibility = View.GONE
                btnClose.visibility = View.GONE
            }
            BtnDialogNoticeEnum.BTN_LEFT -> {
                btnLeft.visibility = View.VISIBLE
                btnRight.visibility = View.GONE
                view.visibility = View.GONE
                btnClose.visibility = View.GONE
            }
        }

        if (alertDialogType.isSuccess) {
            imageIcon.visibility = View.VISIBLE
            tvTitle.visibility = View.GONE
        }

        val msg = alertDialogType.content
        val contentBtnLeft = alertDialogType.contentBtnLeft
        val contentBtnRight = alertDialogType.contentBtnRight
        val title = alertDialogType.title
        btnClose.visibility = if (alertDialogType.isShowBtnClose) View.VISIBLE else View.GONE

        if (contentBtnLeft.isNotEmpty())
            btnLeft.text = contentBtnLeft
        if (contentBtnRight.isNotEmpty())
            btnRight.text = contentBtnRight
        if (msg.isNotEmpty())
            tvContent.text = msg
        if (title.isNotEmpty())
            tvTitle.text = title

        if (alertDialogType.btnLeftBg != 0)
            btnLeft.background = ContextCompat.getDrawable(context, alertDialogType.btnLeftBg)
        if (alertDialogType.btnRightBg != 0)
            btnRight.background = ContextCompat.getDrawable(context, alertDialogType.btnRightBg)
        if (alertDialogType.icon != 0)
            imageIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    alertDialogType.btnLeftBg
                )
            )


        btnLeft.setOnClickListener {
            eventDismiss = BtnDialogNoticeEnum.BTN_LEFT
            dismiss()
        }
        btnRight.setOnClickListener {
            eventDismiss = BtnDialogNoticeEnum.BTN_RIGHT
            dismiss()
        }
        btnClose.setOnClickListener {
            eventDismiss = BtnDialogNoticeEnum.NO_BTN
            dismiss()
        }

        setOnDismissListener {
            if (msg.contains(Defaults.TOKEN_FAILURE_MSG) || alertDialogType.codeResponse == ResultCode.TOKEN_FAILURE)
//                startActivityExt<LoginActivity>()
                alertDialogType.listener?.let { it.onDismiss(eventDismiss) }
        }

        if (window != null) {
            val layoutParams = window!!.attributes
            layoutParams.windowAnimations = R.style.DialogAnimationDuration300
        }

        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        window!!.setLayout(width, height)
        show()
    }
}

@FunctionalInterface
interface DialogNoticeListen {
    fun onDismiss(event: BtnDialogNoticeEnum)
}


/**
 * Dialog loading
 */
var dialogLoading: NSDialogLoading? = null
var timerLoading = 0L
var timerDelayLoading: Timer? = null

private fun Context.onDialogLoading(isShadown: Boolean) {

    if (dialogLoading == null) {
        timerDelayLoading?.let { it.cancel() }
        timerLoading = SystemClock.currentThreadTimeMillis()

        dialogLoading = NSDialogLoading.switcher(isShadown)
        dialogLoading!!.isCancelable = false
        var manager: FragmentManager? = null

        if (this is AppCompatActivity)
            manager = this.supportFragmentManager
        else
            manager = (this as Fragment).childFragmentManager

        dialogLoading!!.show(manager, "NSDialogLoading")
    }
}

fun Context.onDialogLoadingShadow() {
    onDialogLoading(true)
}

fun Context.onDialogLoadingNotShadow() {
    onDialogLoading(false)
}

fun Context.onDismissDialogLoading() {
    try {
        val timerLoad = 800L
        val endTime =
            SystemClock.currentThreadTimeMillis() - timerLoading
        if (dialogLoading != null) {
            if (endTime < timerLoad) {
                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        dialogLoading!!.dismiss()
                        dialogLoading = null
                    } catch (e: Exception) {
                    }
                }, timerLoad)
            } else {
                dialogLoading!!.dismiss()
                dialogLoading = null
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun Activity.onRating() {
    try {
        var dialogRating = Dialog(this)
        dialogRating.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogRating.setContentView(R.layout.view_rate_app)
        val ratingBar = dialogRating.findViewById<RatingBar>(R.id.simpleRatingBar)
        val tvTitle = dialogRating.findViewById<TextView>(R.id.tvTitle)
        val btnLater = dialogRating.findViewById<TextView>(R.id.btnLater)
        val btnSubmit = dialogRating.findViewById<TextView>(R.id.btnSubmit)


        btnLater.setOnClickListener {
            dialogRating.dismiss()
        }
        var rating = 0F
        ratingBar.setOnRatingChangeListener {
            rating = it
        }
        btnSubmit.setOnClickListener {

            presenter().onRating(rating.toInt())
                .subscribeUntilDestroy(this, LoadingType.SHOW_SHADOW) {
                    onNext {
                        dialogRating.dismiss()
                    }
                }

            if (rating >= 4) {
                val appPackageName = packageName
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$appPackageName")
                        )
                    )
                } catch (exception: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            }
            dialogRating.dismiss()
        }

        val w: Int = ViewGroup.LayoutParams.MATCH_PARENT
        val h: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogRating.window!!.setLayout(w, h)
        dialogRating.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRating.show()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}







