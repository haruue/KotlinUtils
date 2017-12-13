@file:Suppress("unused")

package moe.haruue.util.kotlin

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log

/**
 * should invoke the [debug]
 *
 * You need only set it once, such like in your [android.app.Application.onCreate].
 * If you use [debug] {} before set it,  a ERROR level log will be print with `true` returned.
 *
 * A example would be like
 * ```
 * debugMode = BuildConfig.DEBUG
 * ```
 */
var debugMode: Boolean? = null
    get() {
        if (field == null) {
            Log.e("DebugUtils", "Please set debugMode before use the debug {}")
        }
        return field ?: true
    }

/**
 * Auto set the [debugMode] with the value of android:debuggable in <application>
 */
fun Context.autoDebugMode() {
    val pm = packageManager
    val info = pm.getApplicationInfo(packageName, 0)
    debugMode = info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
}

/**
 * run [r] in [debugMode]
 *
 * make sure you have set the [debugMode] or call [autoDebugMode] before you use it
 *
 * ```
 * debug {
 *     // do something you like in debug mode...
 * }
 * ```
 */
inline fun debug(r: () -> Unit) {
    return when {
        debugMode ?: true -> r()
        else -> {}
    }
}

