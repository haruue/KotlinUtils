@file:Suppress("unused")

package moe.haruue.util.kotlin

import android.content.Context
import android.support.annotation.IntDef
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * @suppress
 */
@IntDef(Toast.LENGTH_SHORT.toLong(), Toast.LENGTH_LONG.toLong())
@Retention(AnnotationRetention.SOURCE)
annotation class Duration

/**
 * make toast [msg] with [Context.getApplicationContext] and show
 *
 * @see [Toast.LENGTH_SHORT]
 * @see [Toast.LENGTH_LONG]
 */
fun Context.toast(msg: String, @Duration duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, msg, duration).show()
}

/**
 * make toast in [resId] string id with [Context.getApplicationContext] and show
 *
 * @see [Toast.LENGTH_SHORT]
 * @see [Toast.LENGTH_LONG]
 */
fun Context.toast(@StringRes resId: Int, @Duration duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, resId, duration).show()
}
