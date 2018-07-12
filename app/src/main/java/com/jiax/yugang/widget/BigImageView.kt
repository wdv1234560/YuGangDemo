package com.jiax.yugang.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Base64InputStream
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.io.InputStream
import java.util.jar.Attributes

class BigImageView : View {

    var mDecoder: BitmapRegionDecoder? = null
    //图片宽高
    private var mImgWidth = 0
    private var mImgHeight = 0

    //绘制的区域
    private val mRect = Rect()

    var mDownX = 0f
    var mDownY = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    companion object {
        private val mOptions = BitmapFactory.Options()

        init {
            mOptions.inPreferredConfig = Bitmap.Config.RGB_565
        }
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = event?.x
                mDownY = event?.y
            }

            MotionEvent.ACTION_MOVE -> {
                val moveToX = event.x
                val moveToY = event.y
                val xDistance = (moveToX - mDownX).toInt()
                val yDistance = (moveToY - mDownY).toInt()
                if (mImgWidth > width) {
                    mRect.offset(-xDistance, 0)
                    checkWidht()
                    invalidate()
                }

                if (mImgHeight > height) {
                    mRect.offset(0, -yDistance)
                    checkHeight()
                    invalidate()
                }
            }
        }
        return true
    }

    private fun checkWidht() {

        val rect = mRect
        var imgWidth = mImgWidth
        var imgHeight = mImgHeight
        if (rect.right > imgWidth) {
            rect.right = imgWidth
            rect.left = imgWidth - width
        }

        if (rect.left < 0) {
            rect.left = 0
            rect.right = width
        }

    }

    private fun checkHeight() {
        val rect = mRect
        var imgWidth = mImgWidth
        var imgHeight = mImgHeight
        if (rect.bottom > imgHeight) {
            rect.bottom = height
            rect.top = imgHeight - height
        }

        if (rect.top < 0) {
            rect.top = 0
            rect.bottom = height
        }

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        //默认直接显示图片的顶部区域
        mRect.left = 0
        mRect.top = 0
        mRect.right = mRect.left + width
        mRect.bottom = mRect.top + height
    }

    override fun onDraw(canvas: Canvas?) {
        if (mDecoder == null)
            return
        val bitmap = mDecoder?.decodeRegion(mRect, mOptions)
        if (bitmap != null)
            canvas?.drawBitmap(bitmap, 0f, 0f, null)
    }

    public fun setInputStream(inputStream: InputStream) {

        try {
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false)
            inputStream.reset()
            var tempOp = BitmapFactory.Options()
            tempOp.inJustDecodeBounds = true//设置为true，将不把图片的像素加载到内存，仅加载一些额外的数据到Option中
            BitmapFactory.decodeStream(inputStream, null, tempOp)
            mImgWidth = tempOp.outWidth
            mImgHeight = tempOp.outHeight

            requestLayout()
            invalidate()

        } catch (e: Exception) {
            e.printStackTrace()

        } finally {
            try {
                if (inputStream != null)
                    inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}