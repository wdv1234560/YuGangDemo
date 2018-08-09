package com.jiax.yugang.jni

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jiax.yugang.java.JniFiledUtil
import com.jiax.yugang.java.SimpleJniUtil

/**
 * date     2018/7/25 17:43
 * author   caojiaxu
 * desc
 */
class JniDemoActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val stringFromJNI = stringFromJNI() + integerFromJNI()
        val sayHello = sayHello("ddd")
        Log.d(TAG, sayHello)
        var array = intArrayOf(1, 2, 3, 4, 5)

        val sumArray = SimpleJniUtil.sumArray(array)
        Log.d(TAG, "sumArray=" + sumArray);
        val addArray = SimpleJniUtil.arrayAddTen(array)
        printfArray(addArray)
        val arrayNew = getArray(10)
        printfArray(arrayNew)
        val checkPwd = checkPwd("123456")
        val checkPwd1 = checkPwd("123")

        val init2DArray = SimpleJniUtil.init2DArray(3)
        var i = 0
        for (one in init2DArray) {
            var j = 0
            for (two in one) {
                Log.d(TAG, String.format("arr[%d][%d]=%d", i, j, two))
                j++
            }
            i++
        }

        Log.d(TAG, "checkPwd===" + checkPwd + "--checkPwd1==" + checkPwd1)
        JniFiledUtil().nativeMethod()
        JniFiledUtil.nativeMethod2()
    }

    fun printfArray(array: IntArray) {
        for (i in array.indices) {

            Log.d(TAG, "==" + array[i])
        }
    }

    external fun stringFromJNI(): String
    external fun integerFromJNI(): Int
    external fun sayHello(jst: String): String
    external fun arrayAddTen(jarr: IntArray): IntArray
    external fun getArray(len: Int): IntArray
    external fun checkPwd(pwd: String): Boolean

    companion object {
        init {

            System.loadLibrary("native-lib")
        }
    }
}