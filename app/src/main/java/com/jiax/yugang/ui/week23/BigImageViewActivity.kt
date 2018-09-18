package com.jiax.yugang.ui.week23

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jiax.yugang.R
import kotlinx.android.synthetic.main.activity_big_img.*

class BigImageViewActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_img)
        button.setOnClickListener {
            bigImageView.setInputStream(assets.open("big.jpg"))
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }
    }

}