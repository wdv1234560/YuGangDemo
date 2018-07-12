package com.jiax.yugang.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.io.InputStream

/**
 * date     2018/7/12 11:04
 * author   caojiaxu
 * desc
 */
class LargeImageView : View {

    var mDecoder: BitmapRegionDecoder? = null

    /**
     * 图形宽高
     * **/
    var mImageWidth = 0
    var mImageHeight = 0
    /**
     * 绘制区域
     * */
    private var mRect = Rect()

    private var mMoveGestureDetector: MoveGestureDetector? = null

    companion object {
        private val mOptions = BitmapFactory.Options()

        init {
            mOptions.inPreferredConfig = Bitmap.Config.RGB_565
        }
    }

    constructor(context: Context?,attrs:AttributeSet) : super(context,attrs){
        init()
    }

    private fun init() {

        //kotlin匿名内部类 object:匿名类{}
        mMoveGestureDetector = MoveGestureDetector(context,object : MoveGestureDetector.OnMoveGestureListener{
            override fun onMoveBegin(detector: MoveGestureDetector): Boolean {

                return true
            }

            override fun onMove(detector: MoveGestureDetector): Boolean {
                var moveX = detector.getMoveX().toInt()
                var moveY = detector.getMoveY().toInt()

                if(mImageWidth>width){
                    mRect.offset(-moveX,0)
                    checkWidht()
                    invalidate()
                }
                if(mImageHeight>height){
                    mRect.offset(0,-moveY)
                    checkHeight()
                    invalidate()
                }
                return true
            }

            override fun onMoveEnd(detector: MoveGestureDetector) {
            }

        })

    }

    private fun checkWidht() {

        val rect = mRect
        var imgWidth = mImageWidth
        var imgHeight = mImageHeight
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
        var imgWidth = mImageWidth
        var imgHeight = mImageHeight
        if (rect.bottom > imgHeight) {
            rect.bottom = height
            rect.top = imgHeight - height
        }

        if (rect.top < 0) {
            rect.top = 0
            rect.bottom = height
        }

    }

    fun setInputStream(inputStream: InputStream){
        mDecoder = BitmapRegionDecoder.newInstance(inputStream,false)
        var tempOption = BitmapFactory.Options()
        //设置为true，将不把图片的像素加载到内存，仅加载一些额外的数据到Option中
        tempOption.inJustDecodeBounds = true
        //高版本，将此流重新定位到最后一次对此输入流调用 mark 方法时的位置。
        inputStream.reset()
        BitmapFactory.decodeStream(inputStream, null, tempOption)
        mImageWidth = tempOption.outWidth
        mImageHeight = tempOption.outHeight
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = measuredWidth
        var height = measuredHeight

        var imgWidth = mImageWidth
        var imgHeight = mImageHeight

        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width
        mRect.bottom = height


    }

    override fun onDraw(canvas: Canvas?) {
        var bitmap = mDecoder!!.decodeRegion(mRect, mOptions)
        canvas!!.drawBitmap(bitmap,0f,0f,null)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mMoveGestureDetector!!.onTouchEvent(event!!)
        return true
    }

}