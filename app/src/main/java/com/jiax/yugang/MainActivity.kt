package com.jiax.yugang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jiax.yugang.week23.BigImageViewActivity
import com.jiax.yugang.week23.SimpleBigImgActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button->startActivity(Intent(baseContext,SimpleBigImgActivity::class.java))
            R.id.button2->startActivity(Intent(baseContext, BigImageViewActivity::class.java))

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(this)
        button2.setOnClickListener(this)
    }
}
