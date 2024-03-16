package com.newsoft.super_note

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.newsoft.super_note.data.rest.exception.NetworkUtil
import com.newsoft.super_note.data.sqlite.DBManager
import com.newsoft.super_note.utils.sharePreferences.SharePreferencesManager


class MainApp : Application() {

    // height screen design iphone xs max = 926
    // height view screen = (height view design / height screen design) x height screen

    override fun onCreate() {
        super.onCreate()
        NetworkUtil.initNetwork(this)
        SharePreferencesManager.initializeInstance(this)
        DBManager.initializeInstance(this)
        FirebaseApp.initializeApp(this)
//        RealmDB.initializeInstance(this)
    }

    var token = ""

    fun getTokenFireBase() {
//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.e("TokenFireBase", "getInstanceId failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                val token = task.result?.token
//
//                // Log and toast
//                Log.e("TokenFireBase", "token:$token")
//            })
    }

}