package com.jiax.yugang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.jiax.yugang.week23.BigImageViewActivity
import com.jiax.yugang.week23.HYLargeImageActivity
import com.jiax.yugang.week23.SimpleBigImgActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button -> startActivity(Intent(baseContext, SimpleBigImgActivity::class.java))
            R.id.button2 -> startActivity(Intent(baseContext, BigImageViewActivity::class.java))
            R.id.button3 -> startActivity(Intent(baseContext, HYLargeImageActivity::class.java))

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)

        val stringFromJNI = stringFromJNI()+integerFromJNI()
        val sayHello = sayHello("ddd")
        Log.d("MainActivity",sayHello)
    }

    external fun stringFromJNI(): String
    external fun integerFromJNI(): Int
    external fun sayHello(jst:String):String
    companion object {
        init {

            System.loadLibrary("native-lib")
        }
    }
}
