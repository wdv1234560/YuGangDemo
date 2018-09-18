package com.jiax.yugang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jiax.yugang.ui.animator.AnimatorActivity
import com.jiax.yugang.ui.jni.JniDemoActivity
import com.jiax.yugang.ui.week23.BigImageViewActivity
import com.jiax.yugang.ui.week23.HYLargeImageActivity
import com.jiax.yugang.ui.week23.SimpleBigImgActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button -> startActivity(Intent(baseContext, SimpleBigImgActivity::class.java))
            R.id.button2 -> startActivity(Intent(baseContext, BigImageViewActivity::class.java))
            R.id.button3 -> startActivity(Intent(baseContext, HYLargeImageActivity::class.java))
            R.id.button4 -> startActivity(Intent(baseContext, JniDemoActivity::class.java))
            R.id.cancel -> startActivity(Intent(baseContext, AnimatorActivity::class.java))

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        cancel.setOnClickListener(this)


    }

}
