@file:Suppress("unused")

package moe.haruue.util.kotlin

import android.os.Parcel
import android.os.Parcelable

/**
 * Read a [MutableList] from [Parcel]
 *
 * Read a [ArrayList] and then cast it to [MutableList]
 *
 * Common usage:
 *
 * ```kotlin
 * this.list = parcel.readMutableList()
 * ```
 *
 */
inline fun <reified T> Parcel.readMutableList(): MutableList<T>? {
    @Suppress("UNCHECKED_CAST")
    return readArrayList(T::class.java.classLoader) as MutableList<T>?
}

/**
 * Make a [Parcelable.Creator] object for the `CREATOR` in a [Parcelable] implementing class
 *
 * Common usage:
 *
 * ```kotlin
 * data class Example(...) : Parcelable {
 *     companion object {
 *         @JvmField
 *         val CREATOR = parcelableCreatorOf<Example>()
 *         //...
 *     }
 * }
 * ```
 *
 */
inline fun <reified T : Parcelable> parcelableCreatorOf(): Parcelable.Creator<T> = object : Parcelable.Creator<T> {
    override fun newArray(size: Int): Array<T?> = arrayOfNulls(size)
    override fun createFromParcel(source: Parcel?): T =
            T::class.java.getDeclaredConstructor(Parcel::class.java).newInstance(source)
}
