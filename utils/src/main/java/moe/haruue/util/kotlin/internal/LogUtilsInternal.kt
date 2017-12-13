@file:Suppress("NOTHING_TO_INLINE", "unused")

package moe.haruue.util.kotlin.internal

import android.util.Log
import moe.haruue.util.kotlin.logd

/**
 * Internal methods and don't use them
 *
 * We keep it public since inline methods are referring them.
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
        return if (tr == null) {
            nt(tag, msg)
        } else {
            wt(tag, msg, tr)
        }
    }

    inline fun internalLogm(msg: String): Int {
        val s = Exception().stackTrace[0]
        val tag = s.className.split(".").last()
        return logd(tag, "${s.className}\$${s.methodName} $msg")
    }
}