package com.jiax.yugang.ui.week23

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jiax.yugang.R
import kotlinx.android.synthetic.main.activity_hy_large_image.*

/**
 * date     2018/7/12 14:10
 * author   caojiaxu
 * desc
 */
class HYLargeImageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hy_large_image)
        liv.setInputStream(assets.open("big.jpg"))
    }
}