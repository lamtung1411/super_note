package com.newsoft.super_note.data.rest


import com.newsoft.super_note.data.model.base.ProfileUserModel
import com.newsoft.super_note.data.model.base.ResponeBase
import com.newsoft.super_note.data.model.base.UploadFileModel
import com.newsoft.super_note.data.rest.exception.ConnectivityInterceptor
import com.newsoft.super_note.utils.sharePreferences.SPKeyEnum
import com.newsoft.super_note.utils.sharePreferences.SharePreferencesManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import rx.Observable
import java.util.concurrent.TimeUnit


class RestAPI {

    companion object {

        const val URL_PRODUCT = "http://sale_manager.tu.appcloud-dev.com/api/"  //0
        const val URL_DEV = "http://appcloud_mobile.quang.appcloud-dev.com/"  //1
        const val URLS3 = "https://vufoodads.s3.ap-southeast-1.amazonaws.com/"
        const val URLMAP = "https://maps.googleapis.com/maps/api/place/"

        private fun setRetrofit(url: String, isLog: Boolean): Retrofit? {
            var retrofit: Retrofit? = null
            var client: OkHttpClient? = null
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

//            if (url == URL && BuildConfig.DEBUG) {
            client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(ConnectivityInterceptor())
//                .addInterceptor(interceptor)
                .build()
//            } else {
//                client = OkHttpClient.Builder()
//                    .connectTimeout(30, TimeUnit.MINUTES)
//                    .readTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(30, TimeUnit.SECONDS)
//                    .build()
//            }

            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
            return retrofit
        }


        private var retrofit: Retrofit? = null

        fun getRestAPI(): IRequestService {
            if (retrofit == null) {
                val typeUrl = SharePreferencesManager.getInstance().getValue(SPKeyEnum.BASE_URL, 0)
                retrofit = setRetrofit(if (typeUrl == 0) URL_PRODUCT else URL_DEV, true)
            }
            return retrofit!!.create(
                IRequestService::class.java
            )
        }

        private var retrofitUpFile: Retrofit? = null

        fun getRestAPIUpFile(): IRequestService {
            if (retrofitUpFile == null)
                retrofitUpFile = setRetrofit(URLS3, false)
            return retrofitUpFile!!.create(IRequestService::class.java)
        }

        private var retrofitMap: Retrofit? = null

        fun getRestAPIMap(): IRequestService {
            if (retrofitMap == null)
                retrofitMap = setRetrofit(URLMAP, true)
            return retrofitMap!!.create(IRequestService::class.java)
        }

    }

    interface IRequestService {

        @FormUrlEncoded
        @POST("account/auth/check_token")
        fun checkToken(
            @Field("token") token: String?
        ): Observable<ProfileUserModel>

        @FormUrlEncoded
        @POST("users/read/notification_unseen")
        fun checkNotificationNew(
            @Field("token") token: String?
        ): Observable<ProfileUserModel>


        @FormUrlEncoded
        @POST("users/read/notification_detail")
        fun checkReadNotification(
            @Field("token") token: String?,
            @Field("notification_id") notification_id: String?
        ): Observable<ProfileUserModel>


        @FormUrlEncoded
        @POST("account/auth/loginGoogle")
        fun signInGoogle(
            @Field("access_token") access_token: String?
        ): Observable<ProfileUserModel>

        @FormUrlEncoded
        @POST("users/update/firebase_token")
        fun firebaseToken(
            @Field("token") token: String?,
            @Field("firebase_token") firebase_token: String?
        ): Observable<ProfileUserModel>

        @FormUrlEncoded
        @POST("feedbacks/create/item")
        fun feedBack(
            @Field("token") token: String?,
            @Field("content") content: String?
        ): Observable<ProfileUserModel>

        @FormUrlEncoded
        @POST("users/update/avatar")
        fun avatar(
            @Field("token") token: String?,
            @Field("image") image: String?
        ): Observable<ProfileUserModel>



        @FormUrlEncoded
        @POST("users/create/connect")
        fun connectUser(
            @Field("token") token: String?,
            @Field("code") type: String?
        ): Observable<ResponeBase>

        @FormUrlEncoded
        @POST("users/create/rating")
        fun rating(
            @Field("token") token: String?,
            @Field("star_number") star_number: Int?
        ): Observable<ProfileUserModel>

        @FormUrlEncoded
        @POST("users/create/checkin")
        fun checkInContent(
            @Field("token") token: String?,
            @Field("address") address: String?,
            @Field("content") content: String?,
            @Field("lat") lat: Double?,
            @Field("lng") lng: Double?
        ): Observable<ProfileUserModel>


        @FormUrlEncoded
        @POST("users/update/location")
        fun updateLocation(
            @Field("token") token: String?,
            @Field("lat") lat: Double?,
            @Field("lng") lng: Double?
        ): Observable<ResponeBase>




        @FormUrlEncoded
        @POST("service/auth/create/password")
        fun verifyForgotPass(
            @Header("Authorization") authorization: String,
            @Field("auth_token") auth_token: String?,
            @Field("password") otp: String?,
            @Field("phone") phone: String?
        ): Observable<ResponeBase>

        @FormUrlEncoded
        @POST("service/auth/create/phone_otp")
        fun sendOTP(
            @Header("Authorization") authorization: String,
            @Header("Content-Type") content: String,
            @Field("phone") phone: String?
        ): Observable<ResponeBase>

        @FormUrlEncoded
        @POST("service/auth/create/otp_verify")
        fun OTPVerify(
            @Header("Authorization") authorization: String,
            @Header("Content-Type") content: String,
            @Field("otp_session") otpSession: String?,
            @Field("otp_code") otpCode: String?
        ): Observable<ResponeBase>

        @FormUrlEncoded
        @POST("service/auth/create/phone_registry")
        fun registry(
            @Header("Authorization") authorization: String,
            @Field("auth_token") authToken: String?,
            @Field("password") password: String?,
            @Field("name") name: String?,
            @Field("address") address: String?,
            @Field("email") email: String?,
            @Field("ref_code") refCode: String?,
            @Field("device_id") device_id: String?,
            @Field("device_info") device_info: String?
        ): Observable<ProfileUserModel>

        @FormUrlEncoded
        @POST("service/auth/update/info")
        fun updateInforuser(
            @Header("Authorization") authorization: String,
            @Field("user_token") token: String?,
            @Field("name") name: String?,
            @Field("phone") phone: String?,
            @Field("email") email: String?,
            @Field("address") address: String?,
            @Field("image") image: String?
        ): Observable<ProfileUserModel>

        @FormUrlEncoded
        @POST("service/auth/update/change_password")
        fun changePassword(
            @Header("Authorization") authorization: String,
            @Field("old_password") oldpassword: String?,
            @Field("new_password") newpassword: String?,
            @Field("confirm_password") confirm_password: String?,
            @Field("user_token") token: String?
        ): Observable<ProfileUserModel>

        @FormUrlEncoded
        @POST("users/create/get_key")
        fun uploadFileName(
            @Field("token") token: String?,
            @Field("file_name") file_name: String?,
            @Field("file_size") file_size: String?
        ): Observable<UploadFileModel>

        @PUT
        fun uploadToServer(
            @Header("Content-Type") contentType: String,
            @Url url: String,
            @Body video: RequestBody
        ): Observable<UploadFileModel>

        @FormUrlEncoded
        @POST("service/orders/create/address")
        fun addAddress(
            @Header("Authorization") authorization: String,
            @Field("user_token") user_token: String?,
            @Field("title") title: String?,
            @Field("phone") phone: String?,
            @Field("address") address: String?,
            @Field("province_id") province_id: String?,
            @Field("district_id") district_id: String?,
            @Field("sub_district_id") sub_district_id: String?,
            @Field("selected") selected: Int?
        ): Observable<ResponeBase>

        @FormUrlEncoded
        @POST("service/apps/read/check_version")
        abstract fun checkVersion(
            @Header("Authorization") authorization: String,
            @Field("role") role: String
        ): Observable<ResponeBase>


    }


    class OAuthInterceptor(private val tokenType: String, private val accessToken: String) :
        Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request =
                request.newBuilder().header("Authorization", "$tokenType $accessToken").build()
            return chain.proceed(request)
        }
    }
}