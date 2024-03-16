//package com.newsoft.super_note.ui.login
//
//import android.content.Intent
//import android.util.Log
//import com.google.android.gms.auth.api.signin.*
//import com.google.android.gms.common.api.ApiException
//import com.google.firebase.auth.FirebaseAuth
//import com.newsoft.super_note.R
//import com.newsoft.super_note.data.rest.exception.LoadingType
//import com.newsoft.super_note.data.rest.exception.subscribeUntilDestroy
//import com.newsoft.super_note.ui.base.AppLayerManager
//import com.newsoft.super_note.ui.base.BaseActivity
//import com.newsoft.super_note.ui.main.MainActivity
//import com.newsoft.super_note.utils.presenter
//import com.newsoft.nscustom.ext.context.startActivityExtFinish
//import kotlinx.android.synthetic.main.activity_login.*
//
//
//class LoginActivity : BaseActivity(R.layout.activity_login) {
//    private val TAG = "LoginActivity"
//
//    private var mSignInClient: GoogleSignInClient? = null
//    private var mFirebaseAuth: FirebaseAuth? = null
//    private val RC_SIGN_IN = 1234
//
//    override fun onCreate() {
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.your_web_client_id))
//            .requestEmail()
//            .build()
//        mSignInClient = GoogleSignIn.getClient(this, gso)
//
//        mFirebaseAuth = FirebaseAuth.getInstance()
//
//        mSignInClient!!.signOut()
//        btnLogin.setOnClickListener {
//            val signInIntent = mSignInClient!!.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                Log.e("token", " " + account.idToken)
//                presenter().onSignInGoogle(account.idToken!!)
//                    .subscribeUntilDestroy(this, LoadingType.SHOW_SHADOW) {
//                        onNext {
//                            AppLayerManager.getInstance().profileUserModel = it
//                            presenter().setToken(it.token)
//                            startActivityExtFinish<MainActivity>()
//                        }
//                    }
//            } catch (e: ApiException) {
//                Log.w(TAG, "Google sign in failed", e)
//            }
//        }
//    }
//
//}
