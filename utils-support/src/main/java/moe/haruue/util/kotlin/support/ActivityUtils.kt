@file:Suppress("unused")

package moe.haruue.util.kotlin.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Start activity [T] and you can modify the intent in [block]
 *
 * This method will auto add [Intent.FLAG_ACTIVITY_NEW_TASK] if [Context] is not a [Activity]
 *
 * Common usage:
 *
 * ```kotlin
 * startActivity<ExampleActivity> {
 *     putExtra("param", param)
 * }
 * ```
 *
 */
inline fun <reified T> Fragment.startActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this.activity, T::class.java)
    intent.block()
    this.startActivity(intent)
}

/**
 * options version of another [startActivity]
 *
 * @param options see [android.app.ActivityOptions]
 */
inline fun <reified T> Fragment.startActivity(options: Bundle, block: Intent.() -> Unit = {}) {
    val intent = Intent(this.activity, T::class.java)
    intent.block()
    this.startActivity(intent, options)
}

/**
 * Start activity [T] for result and you can modify the intent in [block]
 *
 * Common usage:
 *
 * ```kotlin
 * startActivityForResult<ExampleActivity>(REQUEST_EXAMPLE) {
 *     putExtra("param", param)
 * }
 * ```
 *
 */
inline fun <reified T> Fragment.startActivityForResult(requestCode: Int, block: Intent.() -> Unit = {}) {
    val intent = Intent(this.activity, T::class.java)
    intent.block()
    startActivityForResult(intent, requestCode)
}

/**
 * options version of another [startActivityForResult]
 *
 * @param options see [android.app.ActivityOptions]
 */
inline fun <reified T> Fragment.startActivityForResult(requestCode: Int, options: Bundle, block: Intent.() -> Unit = {}) {
    val intent = Intent(this.activity, T::class.java)
    intent.block()
    startActivityForResult(intent, requestCode, options)
}
