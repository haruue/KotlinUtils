@file:Suppress("unused")

package moe.haruue.util.kotlin

import android.content.Context
import android.util.TypedValue
import android.view.View


fun Context.dp2px(dp: Float): Int {
    val scale = applicationContext.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun View.dp2px(dp: Float): Int {
    return context.dp2px(dp)
}

fun Context.px2dp(px: Float): Int {
    val scale = applicationContext.resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

fun View.px2dp(px: Float): Int {
    return context.px2dp(px)
}

val Context.screenWidth: Int
    get() {
        val dm = applicationContext.resources.displayMetrics
        return dm.widthPixels
    }

val View.screenWidth: Int
    get() {
        return context.screenWidth
    }

val Context.screenHeight: Int
    get() {
        return screenHeightWithStatusBar - statusBarHeight
    }

val View.screenHeight: Int
    get() {
        return context.screenHeight
    }

val Context.screenHeightWithStatusBar: Int
    get() {
        val dm = applicationContext.resources.displayMetrics
        return dm.heightPixels
    }

val View.screenHeightWithStatusBar: Int
    get() {
        return context.screenHeightWithStatusBar
    }

val Context.navigationBarHeight: Int
    get() {
        var result = 0
        val resourceId = applicationContext.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = applicationContext.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

val View.navigationBarHeight: Int
    get() {
        return context.navigationBarHeight
    }

val Context.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId = applicationContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = applicationContext.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

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

