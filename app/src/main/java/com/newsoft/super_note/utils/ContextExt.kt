package com.newsoft.super_note.utils

import android.app.Activity
import android.app.Service
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.newsoft.nscustom.ext.context.handleCameraPermission
import com.newsoft.nscustom.ext.context.handleWriteStoragePermission
import com.newsoft.super_note.R
import com.newsoft.super_note.data.rest.Presenter
import com.pawegio.kandroid.connectivityManager
import com.pixplicity.sharp.Sharp
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import okhttp3.*
import org.jetbrains.anko.internals.AnkoInternals
import java.io.File
import java.io.IOException
import java.io.InputStream

const val container = R.id.container

fun presenter(): Presenter {
    return Presenter.getInstance()
}

/**
 * Check if internet is available
 * returns [Boolean]
 */
fun Context.isInternetAvailable() = connectivityManager?.activeNetworkInfo?.isConnected == true

/**
 * Handle connection availability
 * @param available
 * @param unavailable
 */
fun Context.internetConnectivityHandler(
    available: (() -> Unit),
    unavailable: (() -> Unit)? = null
) {
    if (isInternetAvailable()) available.invoke() else unavailable?.invoke()
}


fun Context.getConnectionType(): String {
    if (!isInternetAvailable())
        return "-"

    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when (cm.activeNetworkInfo?.type) {
        ConnectivityManager.TYPE_WIFI -> "WiFi"
        ConnectivityManager.TYPE_MOBILE -> "Cellular"
        else -> "-"
    }
}



inline fun <reified T : Activity> View.startActivity(vararg params: Pair<String, Any?>) =
    AnkoInternals.internalStartActivity(context, T::class.java, params)

inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) =
    AnkoInternals.internalStartActivityForResult(this, T::class.java, requestCode, params)

inline fun <reified T: Service> Fragment.startService(vararg params: Pair<String, Any>) =
    AnkoInternals.internalStartService(context!!, T::class.java, params)

inline fun <reified T : Service> View.startService(vararg params: Pair<String, Any?>) =
    AnkoInternals.internalStartService(context, T::class.java, params)

inline fun <reified T : Service> Context.stopService(vararg params: Pair<String, Any?>) =
    AnkoInternals.internalStopService(this, T::class.java, params)

inline fun <reified T : Service> Fragment.stopService(vararg params: Pair<String, Any?>) =
    AnkoInternals.internalStopService(context!!, T::class.java, params)

inline fun <reified T : Service> View.stopService(vararg params: Pair<String, Any?>) =
    AnkoInternals.internalStopService(context, T::class.java, params)

inline fun <reified T : Any> Context.intentFor(vararg params: Pair<String, Any?>): Intent =
    AnkoInternals.createIntent(this, T::class.java, params)

inline fun <reified T: Any> Fragment.intentFor(vararg params: Pair<String, Any?>): Intent =
    AnkoInternals.createIntent(context!!, T::class.java, params)

inline fun <reified T : Any> View.intentFor(vararg params: Pair<String, Any?>): Intent =
    AnkoInternals.createIntent(context, T::class.java, params)


private var httpClient: OkHttpClient? = null

fun fetchSvg(context: Context, url: String?, target: ImageView) {
    if (httpClient == null) {
        // Use cache for performance and basic offline capability
        httpClient = OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, 5 * 1024 * 1014))
            .build()
    }
    val request: Request = Request.Builder().url(url!!).build()
    httpClient!!.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
//            target.setImageDrawable(R.drawable.fallback_image)
        }

        override fun onResponse(call: Call, response: Response) {
            val stream: InputStream = response.body!!.byteStream()
            Sharp.loadInputStream(stream).into(target)
            stream.close()
        }
    })

}
var REQUEST_CODE_CHOOSE = 1120

fun Fragment.onGetFile(
    mimeTypes: Set<MimeType>,
    maxSelectable: Int = 10,
    codeChoose: Int = REQUEST_CODE_CHOOSE
) {
    requireActivity().handleCameraPermission {
        requireActivity().handleWriteStoragePermission {
            Matisse.from(this)
                .choose(mimeTypes)
                .showSingleMediaType(true)
                .capture(true)
                .captureStrategy(
                    CaptureStrategy(true, "${requireContext().packageName}.fileprovider")
                )
                .countable(true)
                .maxSelectable(maxSelectable)
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen._120sdp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(GlideEngine())
                .forResult(codeChoose)
        }
    }
}

fun AppCompatActivity.onGetFile(
    mimeTypes: Set<MimeType>,
    maxSelectable: Int = 10,
    codeChoose: Int = REQUEST_CODE_CHOOSE
) {
    handleCameraPermission {
        handleWriteStoragePermission {
            Matisse.from(this)
                .choose(mimeTypes)
                .showSingleMediaType(true)
                .capture(true)
                .captureStrategy(
                    CaptureStrategy(true, "$packageName.fileprovider")
                )
                .countable(true)
                .maxSelectable(maxSelectable)
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen._120sdp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(GlideEngine())
                .forResult(codeChoose)
        }
    }


}
fun Context.getFileName(uri: Uri): String? = when(uri.scheme) {
    ContentResolver.SCHEME_CONTENT -> getContentFileName(uri)
    else -> uri.path?.let(::File)?.name
}

private fun Context.getContentFileName(uri: Uri): String? = runCatching {
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        cursor.moveToFirst()
        return@use cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME).let(cursor::getString)
    }
}.getOrNull()
