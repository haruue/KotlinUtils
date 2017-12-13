@file:Suppress("unused")

package moe.haruue.util.kotlin

import android.content.Context
import android.util.TypedValue
import android.view.View

/**
 * Convert dip to pixel
 */
fun Context.dp2px(dp: Float): Int {
    val scale = applicationContext.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

/**
 * Convert dip to pixel
 */
fun View.dp2px(dp: Float): Int {
    return context.dp2px(dp)
}

/**
 * Convert pixel to dip
 */
fun Context.px2dp(px: Float): Int {
    val scale = applicationContext.resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

/**
 * Convert pixel to dip
 */
fun View.px2dp(px: Float): Int {
    return context.px2dp(px)
}

/**
 * The absolute width of the available display size in pixels.
 *
 * @see [android.util.DisplayMetrics.widthPixels]
 */
val Context.screenWidth: Int
    get() {
        val dm = applicationContext.resources.displayMetrics
        return dm.widthPixels
    }

/**
 * The absolute width of the available display size in pixels.
 *
 * @see [android.util.DisplayMetrics.widthPixels]
 */
val View.screenWidth: Int
    get() {
        return context.screenWidth
    }

/**
 * The absolute height of the available display size in pixels, without status bar.
 */
val Context.screenHeight: Int
    get() {
        return screenHeightWithStatusBar - statusBarHeight
    }

/**
 * The absolute height of the available display size in pixels, without status bar.
 */
val View.screenHeight: Int
    get() {
        return context.screenHeight
    }

/**
 * The absolute height of the available display size in pixels.
 *
 * @see [android.util.DisplayMetrics.heightPixels]
 */
val Context.screenHeightWithStatusBar: Int
    get() {
        val dm = applicationContext.resources.displayMetrics
        return dm.heightPixels
    }

/**
 * The absolute height of the available display size in pixels.
 *
 * @see [android.util.DisplayMetrics.heightPixels]
 */
val View.screenHeightWithStatusBar: Int
    get() {
        return context.screenHeightWithStatusBar
    }

/**
 * The absolute height of the navigation bar size in pixels.
 */
val Context.navigationBarHeight: Int
    get() {
        var result = 0
        val resourceId = applicationContext.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = applicationContext.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

/**
 * The absolute height of the navigation bar size in pixels.
 */
val View.navigationBarHeight: Int
    get() {
        return context.navigationBarHeight
    }

/**
 * The absolute height of the status bar size in pixels.
 */
val Context.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId = applicationContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = applicationContext.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

/**
 * The absolute height of the action bar size in pixels.
 */
val Context.actionBarHeight: Int
    get() {
        var actionBarHeight = 0

        val tv = TypedValue()
        if (applicationContext.theme
                .resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data, applicationContext.resources.displayMetrics)
        }
        return actionBarHeight
    }

