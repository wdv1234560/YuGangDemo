package com.jiax.yugang.ui.animator

import android.animation.Animator
import android.animation.FloatEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jiax.yugang.R
import kotlinx.android.synthetic.main.activity_animator.*

/**
 * date     2018/9/18 10:32
 * author   caojiaxu
 * desc
 */
class AnimatorActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = AnimatorActivity::class.java.name
    var valueAnimator: ValueAnimator? = null
    var objectAnimator: ValueAnimator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)

        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator?.duration = 1000
        valueAnimator?.repeatCount = 4
        /**
         * repeatMode：重放模式
         * REVERSE:第二次播放动画，逆转到原来值
         * RESTART：第二次播放动画，从原始值开始
         *
         * */
        valueAnimator?.repeatMode = ValueAnimator.REVERSE

        valueAnimator?.addUpdateListener {
            textView.textSize = ((it.animatedValue as Float) * 15).toFloat()
            textView.setTextColor(((it.animatedValue as Float) + Color.BLUE).toInt())
//            Log.d(TAG, "animatedValue=" + it.animatedValue.toString())
        }
        // 初始alpha值为1，scale值为1
        // 结束alpha值为0，scale值为2，相当于透明度变为0，尺寸放大到2倍
        objectAnimator = ValueAnimator.ofObject(MyEvaluator(), ValueObject(1f, 1f), ValueObject(0f, 2f))
        objectAnimator?.duration = 2000
        objectAnimator?.repeatMode = ValueAnimator.RESTART
        objectAnimator?.repeatCount = ValueAnimator.INFINITE//无限播放
        objectAnimator?.addUpdateListener {
            val valueObject = it.animatedValue as ValueObject
            imageView.alpha = valueObject.alphaValue
            imageView.scaleX = valueObject.scaleValue
            imageView.scaleY = valueObject.scaleValue
        }
        objectAnimator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

                Log.d(TAG, "repeat")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.d(TAG, "end")
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.d(TAG, "cancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.d(TAG, "start")
            }

        })
        start.setOnClickListener(this)
        cancel.setOnClickListener(this)

    }


    fun start() {
        douLoadingView.start()
//        valueAnimator?.start()
        objectAnimator?.start()
    }

    fun cancel() {
        valueAnimator?.cancel()
        objectAnimator?.cancel()
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.start -> start()
            R.id.cancel -> cancel()

        }
    }

}