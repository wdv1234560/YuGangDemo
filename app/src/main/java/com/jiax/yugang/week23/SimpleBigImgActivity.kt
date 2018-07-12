package com.jiax.yugang.week23

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jiax.yugang.R
import kotlinx.android.synthetic.main.activity_main.*

class SimpleBigImgActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_big_img)
        val iv = findViewById<ImageView>(R.id.imageView)
        button.setOnClickListener {
            loadBigImageByInSampleSize(iv)
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadBigImageByInSampleSize(imageView: ImageView) {
        try {

            val inputStream = assets.open("big.jpg")
            var options = BitmapFactory.Options()
            options.inJustDecodeBounds = true//设置为true，将不把图片的像素加载到内存，仅加载一些额外的数据到Option中
            BitmapFactory.decodeStream(inputStream, null, options)
            val imgWidth = options.outWidth
            val imgHeight = options.outHeight

            //获取屏幕宽高
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels

            //计算采样率
            val scaleX = imgWidth / screenWidth
            val scaleY = imgHeight / screenHeight
            var scale = 1

            //采样率依照最大的方向为准
            if (scaleX > scaleY && scaleY >= 1) {
                scale = scaleX
            }
            if (scaleX < scaleY && scaleX >= 1) {
                scale = scaleY
            }
            Log.d(TAG, "loadBigImgByInSampleSize:" + scale)

            //false表示读取图片像素数组到内存中，依照设定的采样率
            options.inJustDecodeBounds = false
            options.inSampleSize = scale
            /*流已经关闭的解决方法
            出现这个问题的主要原因是解析网络流的代码写在了流关闭后，只需要分析清楚流在什么时候关闭即可。

            decodeStream调用了两次的问题分析和解决方法
            本文主要对此情况进行分析：
            首先是如何重现这种问题，很显然为了得到图片的分辨率，一般都会先把inJustDecodeBounds设置为true，解析图片，这时候的decodeStream是必定返回null，api本身设计就是这样，
            如果直接不解析分辨率decodeStream的话可以正常返回Bitmap对象，这个原因很简单，第一次decodeStream时已经操作过inputstream了，这时候流的操作位置已经移动了，如果再次decodeStream则不是从流的起始位置解析，所以无法解析出Bitmap对象。
            只需要添加下面代码使流读写位置恢复为起始位置即可：
            */
            inputStream.reset()
            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}