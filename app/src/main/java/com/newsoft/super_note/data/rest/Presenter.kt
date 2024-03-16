package com.newsoft.super_note.data.rest

import android.content.Context
import android.net.Uri
import android.util.Log
import com.newsoft.super_note.data.model.base.ProfileUserModel
import com.newsoft.super_note.data.model.base.ResponeBase
import com.newsoft.super_note.data.model.base.UploadFileModel
import com.newsoft.super_note.data.model.request.BaseRequestModel
import com.newsoft.super_note.data.rest.exception.LoadingType
import com.newsoft.super_note.data.rest.exception.subscribeUntilDestroy
import com.newsoft.super_note.utils.InputStreamRequestBody
import com.newsoft.super_note.utils.onDialogLoadingShadow
import com.newsoft.super_note.utils.onDismissDialogLoading
import com.newsoft.super_note.utils.sharePreferences.SPKeyEnum
import com.newsoft.super_note.utils.sharePreferences.SharePreferencesManager
import com.newsoft.nscustom.ext.context.getDeviceName
import com.newsoft.super_note.R
import com.newsoft.super_note.utils.getFileName
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


open class Presenter {

    private var restAPI = RestAPI.getRestAPI()
    private var token = ""
    private var authorization = ""
    private var contentType = "application/x-www-form-urlencoded"
    private var requestBody: BaseRequestModel? = null

    companion object {
        private var sInstance: Presenter? = null

        @Synchronized
        fun getInstance(): Presenter {
            if (sInstance == null)
                sInstance = Presenter()
            return sInstance as Presenter
        }
    }

    constructor() {
        token = SharePreferencesManager.getInstance().getValue(SPKeyEnum.TOKEN)!!
        Log.e("token", token)
        val typeUrl = SharePreferencesManager.getInstance().getValue(SPKeyEnum.BASE_URL, 0)
        requestBody = BaseRequestModel()
    }

    fun setToken(token: String) {
        this.token = token
        SharePreferencesManager.getInstance().setValue(SPKeyEnum.TOKEN, token)
    }

    //TODO: Login
    fun onCheckToken(): Observable<ProfileUserModel> {
        return restAPI.checkToken(token)
    }

    fun onCheckNotificationNew(): Observable<ProfileUserModel> {
        return restAPI.checkNotificationNew(token)
    }

    fun onSignInGoogle(token_auth: String): Observable<ProfileUserModel> {
        return restAPI.signInGoogle(token_auth)
    }


    fun onFeedback(content: String): Observable<ProfileUserModel> {
        return restAPI.feedBack(token, content)
    }


    fun onRating(star_number: Int): Observable<ProfileUserModel> {
        return restAPI.rating(token, star_number)
    }

    fun onFirebaseToken(firebase_token: String): Observable<ProfileUserModel> {
        return restAPI.firebaseToken(token, firebase_token)
    }

    fun onCheckReadNotification(notification_id: String): Observable<ProfileUserModel> {
        return restAPI.checkReadNotification(token, notification_id)
    }




    fun onUpdateLocation(lat: Double, lng: Double): Observable<ResponeBase> {
        return restAPI.updateLocation(token, lat, lng)
    }

    fun onUpdateAvatar(image: String): Observable<ProfileUserModel> {
        return restAPI.avatar(token, image)
    }



    fun onCheckInContent(
        address: String,
        content: String,
        lat: Double,
        lng: Double
    ): Observable<ProfileUserModel> {
        return restAPI.checkInContent(token, address, content, lat, lng)
    }


    fun onConnectUser(code: String): Observable<ResponeBase> {
        return restAPI.connectUser(token, code)
    }



    //TODO: Registration

    fun onSendOTP(phone: String) =                           // nhận otp đăng ký
        restAPI.sendOTP(authorization, contentType, phone)

    fun onOTPVertify(otpSession: String, otpCode: String) =    // xác nhận otp đăng ký
        restAPI.OTPVerify(authorization, contentType, otpSession, otpCode)

    fun onRegistry(
        authToken: String,
        password: String,
        name: String,
        address: String,
        email: String,
        refCode: String
    ) =          // đăng ký thông tin
        restAPI.registry(
            authorization,
            authToken,
            password,
            name,
            address,
            email,
            refCode,
            "0",// fribase
            getDeviceName()
        )

    fun onVerifyForgotPass(auth_token: String, password: String, phone: String) =
        restAPI.verifyForgotPass(authorization, auth_token, password, phone)

    //TODO: Change profile

    fun onUpdateInforuser(
        name: String,
        phone: String,
        email: String,
        address: String,
        image: String
    ) =
        restAPI.updateInforuser(authorization, token, name, phone, email, address, image)

    fun onChangePassword(oldpassword: String, newpassword: String) =
        restAPI.changePassword(authorization, oldpassword, newpassword, newpassword, token)


    //TODO:-------------------------------- upload file ---------------------------------------
    fun onUploadFile(
        context: Context,
        uri: Uri,
        listener: UploadFileListener
    ) {
        context.onDialogLoadingShadow()
        //TODO: upload file name giữ chỗ
        val file_name = context.getFileName(uri)!!
        restAPI.uploadFileName(token, file_name, "0")
            .subscribeUntilDestroy(context, LoadingType.NOT) {
                onNext {
                    onUploadToServer(context, it, uri, listener)
                }
            }
    }

    //TODO: gửi file lên server
    private fun onUploadToServer(
        context: Context,
        uploadModel: UploadFileModel,
        uri: Uri,
        listener: UploadFileListener
    ) {
        val part = uploadModel.upload.replace(RestAPI.URLS3, "")
        context.contentResolver.getType(uri)?.let { mimeType ->
            val requestBody = InputStreamRequestBody(context.contentResolver, uri)
            RestAPI.getRestAPIUpFile().uploadToServer(mimeType, part, requestBody)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listener.onResponse(uploadModel)
                    context.onDismissDialogLoading()
                }, {
                    listener.onResponse(uploadModel)
                    context.onDismissDialogLoading()
                })
        }
    }


    fun onCheckVersion() =
        restAPI.checkVersion(authorization, "android")

    //TODO:-------------------------------------------------------------------------------------

    interface UploadFileListener {
        fun onResponse(uploadFileModel: UploadFileModel)
    }

    fun onAddAddress(
        title: String,
        phone: String,
        address: String,
        province_id: String,
        district_id: String,
        sub_district_id: String,
        selected: Int
    ) =
        restAPI.addAddress(
            authorization,
            token,
            title,
            phone,
            address,
            province_id,
            district_id,
            sub_district_id,
            selected
        )

}