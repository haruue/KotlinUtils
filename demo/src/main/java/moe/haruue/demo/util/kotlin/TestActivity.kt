package moe.haruue.demo.util.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import moe.haruue.util.kotlin.*

/**
 *
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        globalLogConfig {
            shouldLogForLevel = { it < Log.ASSERT }
        }
        example1("hello", 233)
        example2()
        LongLongLongLongLongLongLongLongLongLongName().example1("world", 666)
    }

    fun example1(str: String, int: Int): String {
        logm(str, int)
        return "$str, $int, ${str.logthis(msg = "example of logthis, example1()#end#str") + int}".logr()
    }

    fun example2() {
        logv("logv")
        logd("logd")
        logi("logi")
        logw("logw")
        loge("loge")
        logwtf("logwtf")
    }

    class LongLongLongLongLongLongLongLongLongLongName {
        fun example1(str: String, int: Int): String {
            logm(str, int)
            return "$str, $int, ${str.logthis(msg = "example of logthis, example1()#end#str") + int}".logr()
        }
    }

}