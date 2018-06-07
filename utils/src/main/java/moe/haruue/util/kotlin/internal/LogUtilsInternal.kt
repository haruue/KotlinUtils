@file:Suppress("NOTHING_TO_INLINE", "unused")

package moe.haruue.util.kotlin.internal

import android.os.Build
import android.util.Log
import moe.haruue.util.kotlin.logd

/**
 * Internal methods and don't use them
 *
 * We keep it public since inline methods are referring them.
 *
 * @suppress
 */
object LogUtilsInternal {
    val shouldLogForLevel = arrayOf(true, true, true, true, true, true, true, true)

    inline fun preCheck(logLevel: Int = Log.DEBUG, r: () -> Int): Int {
        return when {
            shouldLogForLevel[logLevel] -> r()
            else -> 0
        }
    }

    inline fun internalLog(nt: (tag: String, msg: String) -> Int,
                           wt: (tag: String, msg: String, tr: Throwable) -> Int,
                           tag: String, msg: String, tr: Throwable?): Int {
        val shortTag = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            tag.take(23)
        } else {
            tag
        }
        return if (tr == null) {
            nt(shortTag, msg)
        } else {
            wt(shortTag, msg, tr)
        }
    }

    inline fun internalLogm(msg: String): Int {
        val s = Exception().stackTrace[0]
        val tag = s.className.split(".").last()
        return logd(tag, "${s.className}\$${s.methodName} $msg")
    }
}