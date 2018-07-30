package com.jiax.yugang.jni

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * date     2018/7/25 17:43
 * author   caojiaxu
 * desc
 */
class JniDemoActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val stringFromJNI = stringFromJNI()+integerFromJNI()
        val sayHello = sayHello("ddd")
        Log.d("MainActivity",sayHello)
        var array = intArrayOf(1,2,3,4,5)
        val arrayNew = arrayAddTen(array)
        for (i in arrayNew.indices){

            Log.d("MainActivity","=="+arrayNew[i])
        }

        val checkPwd = checkPwd("123456")
        val checkPwd1 = checkPwd("123")


        Log.d("MainActivity","checkPwd==="+checkPwd+"--checkPwd1=="+checkPwd1)
    }

    external fun stringFromJNI(): String
    external fun integerFromJNI(): Int
    external fun sayHello(jst:String):String
    external fun arrayAddTen(jarr:IntArray):IntArray
    external fun checkPwd(pwd:String):Boolean
    companion object {
        init {

            System.loadLibrary("native-lib")
        }
    }
}