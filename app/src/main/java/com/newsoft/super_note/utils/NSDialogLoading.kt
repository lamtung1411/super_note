package com.newsoft.super_note.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.newsoft.super_note.R
import com.victor.loading.rotate.RotateLoading

class NSDialogLoading : DialogFragment() {

    var isShadown = false

    companion object {
        fun switcher(
            isShadown: Boolean
        ): NSDialogLoading {
            val dialogBuyFragment = NSDialogLoading()
            val bundle = Bundle()
            bundle.putBoolean("isShadown", isShadown)
            dialogBuyFragment.setArguments(bundle)
            return dialogBuyFragment
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        isShadown = requireArguments().getBoolean("isShadown")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_loading, null, false)

        var alertDialog: AlertDialog? = null
        if (isShadown) {
            alertDialog = AlertDialog.Builder(requireContext())
                .setView(view)
                .create()
        } else {
            alertDialog = AlertDialog.Builder(requireContext(), R.style.MyDialog2)
                .setView(view)
                .create()
        }
        val rotateloading = view.findViewById<RotateLoading>(R.id.rotateloading)
        rotateloading.start()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        return alertDialog
    }

}