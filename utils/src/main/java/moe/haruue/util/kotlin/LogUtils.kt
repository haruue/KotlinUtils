@file:Suppress("NOTHING_TO_INLINE", "unused")

package moe.haruue.util.kotlin

import android.util.Log
import moe.haruue.util.kotlin.LogUtilsInternal.internalLog
import moe.haruue.util.kotlin.LogUtilsInternal.internalLogm
import moe.haruue.util.kotlin.LogUtilsInternal.preCheck
import java.util.*

object GlobalLogConfig {

    class LogFunctions(
            var log: (tag: String, msg: String) -> Int,
            var logt: (tag: String, msg: String, throwable: Throwable) -> Int
    )

    var v = LogFunctions(Log::v, Log::v)
    var i = LogFunctions(Log::i, Log::i)
    var d = LogFunctions(Log::d, Log::d)
    var w = LogFunctions(Log::w, Log::w)
    var e = LogFunctions(Log::e, Log::e)
    var wtf = LogFunctions(Log::wtf, Log::wtf)

    /**
     * Use this method to dismiss logLevel in special level
     *
     * Design for different log level in different environment (such as debug or release),
     * If you just don't want to see it, just use logcat filter instead.
     *
     * Common usage: (dismiss DEBUG or below level logs in release version)
     * ```
     * globalLogConfig {
     *     shouldLogForLevel = { BuildConfig.DEBUG || it > Log.DEBUG }
     * }
     * ```
     *
     * This method will be only called after it set and the result will be cached.
     *
     */
    inline var shouldLogForLevel: (logLevel: Int) -> Boolean
        set(value) {
            arrayOf(Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR, Log.ASSERT).forEach {
                LogUtilsInternal.shouldLogForLevel[it] = value(it)
            }
        }
        get() = { LogUtilsInternal.shouldLogForLevel[it] }
}

/**
 * Internal methods and don't use them
 *
 * We keep it public only for inline methods are referring them.
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

/**
 * use this method to modify global log config in DSL
 */
inline fun globalLogConfig(c: GlobalLogConfig.() -> Unit) {
    GlobalLogConfig.c()
}

/**
 * Replacement of [Log.v]
 */
inline fun logv(tag: String, msg: String, e: Throwable? = null) = preCheck(Log.VERBOSE) {
    internalLog(GlobalLogConfig.v.log, GlobalLogConfig.v.logt, tag, msg, e)
}

/**
 * Replacement of [Log.v], with caller's class simple name as tag
 */
inline fun Any.logv(msg: String, e: Throwable? = null) = logv(this.javaClass.simpleName, msg, e)

/**
 * Replacement of [Log.i]
 */
inline fun logi(tag: String, msg: String, e: Throwable? = null) = preCheck(Log.INFO) {
    return internalLog(GlobalLogConfig.i.log, GlobalLogConfig.i.logt, tag, msg, e)
}

/**
 * Replacement of [Log.i], with caller's class simple name as tag
 */
inline fun Any.logi(msg: String, e: Throwable? = null) = logi(this.javaClass.simpleName, msg, e)

/**
 * Replacement of [Log.d]
 */
inline fun logd(tag: String, msg: String, e: Throwable? = null) = preCheck(Log.DEBUG) {
    internalLog(GlobalLogConfig.d.log, GlobalLogConfig.d.logt, tag, msg, e)
}

/**
 * Replacement of [Log.d], with caller's class simple name as tag
 */
inline fun Any.logd(msg: String, e: Throwable? = null) = logd(this.javaClass.simpleName, msg, e)

/**
 * Replacement of [Log.w]
 */
inline fun logw(tag: String, msg: String, e: Throwable? = null) = preCheck(Log.WARN) {
    internalLog(GlobalLogConfig.w.log, GlobalLogConfig.w.logt, tag, msg, e)
}

/**
 * Replacement of [Log.w], with caller's class simple name as tag
 */
inline fun Any.logw(msg: String, e: Throwable? = null) = logw(this.javaClass.simpleName, msg, e)

/**
 * Replacement of [Log.e]
 */
inline fun loge(tag: String, msg: String, e: Throwable? = null) = preCheck(Log.ERROR) {
    internalLog(GlobalLogConfig.e.log, GlobalLogConfig.e.logt, tag, msg, e)
}

/**
 * Replacement of [Log.e], with caller's class simple name as tag
 */
inline fun Any.loge(msg: String, e: Throwable? = null) = loge(this.javaClass.simpleName, msg, e)

/**
 * Replacement of [Log.wtf]
 *
 * Use [Log.ASSERT] level to control [logwtf]
 */
inline fun logwtf(tag: String, msg: String, e: Throwable? = null) = preCheck(Log.ASSERT) {
    internalLog(GlobalLogConfig.wtf.log, GlobalLogConfig.wtf.logt, tag, msg, e)
}

/**
 * Replacement of [Log.wtf], with caller's class simple name as tag
 *
 * Use [Log.ASSERT] level to control [logwtf]
 */
inline fun Any.logwtf(msg: String, e: Throwable? = null) = logwtf(this.javaClass.simpleName, msg, e)

/**
 * Replacement of AndroidLog (for Java) live template `logm`
 *
 * Pass all parameters to this method to log them
 *
 * ```
 * fun example(a: ClassA, b: ClassB) {
 *     logm(a, b)
 *     // method body...
 * }
 * ```
 *
 */
inline fun logm(vararg args: Any?) = preCheck {
    internalLogm("called with: ${Arrays.toString(args)}")
}

/**
 * Replacement of AndroidLog (for Java) live template `logr`
 *
 * log return value without break the return statement.
 * Just add `.logr()` to the end of return statement.
 *
 * @see [logthis]
 *
 * ```
 * fun example(): Example {
 *     // method body...
 *     return r.logr()
 * }
 * ```
 *
 */
inline fun <reified T> T.logr(): T {
    preCheck {
        val message = if (this is Array<*>) {
            Arrays.toString(this)
        } else {
            this.toString()
        }
        internalLogm("returned with: $message")
    }
    return this
}

/**
 * Log something without break the statement
 *
 * Just add it to the end of a statement
 *
 * ```
 * val result = value1 + value2.logthis(msg = "value2") + value3
 * ```
 */
inline fun <reified T> T.logthis(tag: String? = null, msg: String? = null): T {
    preCheck {
        val ts = if (this is Array<*>) {
            Arrays.toString(this)
        } else {
            this.toString()
        }
        val realTag = if (tag == null) {
            val s = Exception().stackTrace[0]
            s.className.split(".").last()
        } else {
            tag
        }
        val realMessage = with(StringBuilder()) {
            if (msg != null) {
                append(msg)
                append(": ")
            }
            append(ts)
            toString()
        }
        logd(realTag, realMessage)
    }
    return this
}
