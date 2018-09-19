package com.jiax.yugang.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

/**
 * date     2018/9/18 9:42
 * author   caojiaxu
 * desc
 */
class DouLoadingView : View {

    var mLeftPaint: Paint = Paint()
    var mMidPaint: Paint = Paint()
    var mRightPaint: Paint = Paint()
    var valueAnimator: ValueAnimator = ValueAnimator()

    var radius = 20f
    var isStart = false
    var leftMoveX = 0f
    var leftCriX = 0f
    var rightCriX = 0f
    var rightMoveX = 0f
    var criY = 0f

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init()
        initAnimator()
    }

    private fun init() {

        mLeftPaint.color = Color.BLUE
        mLeftPaint.strokeWidth = 2f
        mLeftPaint.isAntiAlias = true
        mLeftPaint.alpha = 240

        mMidPaint.color = Color.BLACK
        mMidPaint.strokeWidth = 2f
        mMidPaint.isAntiAlias = true
        mMidPaint.alpha = 240

        mRightPaint.color = Color.RED
        mRightPaint.strokeWidth = 2f
        mRightPaint.isAntiAlias = true
        mRightPaint.alpha = 240
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension((radius * 4).toInt(), (radius * 2).toInt())
        leftCriX = (measuredWidth / 2) - radius
        rightCriX = (measuredWidth / 2) + radius
        leftMoveX = leftCriX
        rightMoveX = rightCriX
        criY = (measuredHeight / 2).toFloat()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isStart) {
            var midPath = Path()
            var leftPath = Path()
            leftPath.addCircle(leftMoveX, criY, radius, Path.Direction.CCW)
            var rightPath = Path()
            rightPath.addCircle(rightMoveX, criY, radius, Path.Direction.CCW)


            if (animRepeat) {

                canvas?.drawPath(rightPath, mLeftPaint)
                canvas?.drawPath(leftPath, mRightPaint)
            } else {

                canvas?.drawPath(rightPath, mRightPaint)
                canvas?.drawPath(leftPath, mLeftPaint)
            }
            midPath.op(leftPath,rightPath,Path.Op.INTERSECT)
            canvas?.drawPath(midPath,mMidPaint)

        }
    }

    var animRepeat = false
    var animStart = false
    fun initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.repeatMode = ValueAnimator.REVERSE
        valueAnimator.duration = 800
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                animRepeat = !animRepeat
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                animStart = true
            }

        })

        valueAnimator.addUpdateListener {
            //            mLeftPaint.alpha = ((it.animatedValue as Float) * 255).toInt()
            if (animRepeat) {

                leftMoveX = rightCriX - 2 * radius * (it.animatedValue as Float)
                rightMoveX = leftCriX + 2 * radius * (it.animatedValue as Float)
            } else {

                leftMoveX = leftCriX + 2 * radius * (it.animatedValue as Float)
                rightMoveX = rightCriX - 2 * radius * (it.animatedValue as Float)
            }
            invalidate()
        }

    }

    open fun start() {
        isStart = true
        valueAnimator.start()
    }
}