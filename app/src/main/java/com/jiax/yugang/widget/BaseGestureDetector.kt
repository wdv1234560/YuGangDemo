package com.jiax.yugang.widget

import android.content.Context
import android.view.MotionEvent

/**
 * date     2018/7/11 11:54
 * author   caojiaxu
 * desc     手势基类
 */
abstract class BaseGestureDetector {

    protected var mGestrueInProcess = false
    protected var mPreMotionEvent: MotionEvent? = null
    protected var mCurrentMotionEvent: MotionEvent? = null
    protected var mContext: Context? = null

    constructor(context: Context) {
        mContext = context
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        if (!mGestrueInProcess) {
            handleStartProgressEvent(event)
        } else {
            handleInProgressEvent(event)
        }

        return true
    }

    protected fun resetState() {
        if (mPreMotionEvent != null) {
            mPreMotionEvent?.recycle()
            mPreMotionEvent = null
        }

        if (mCurrentMotionEvent != null) {
            mCurrentMotionEvent?.recycle()
            mCurrentMotionEvent = null
        }

        mGestrueInProcess = false

    }

    abstract fun handleInProgressEvent(event: MotionEvent)

    abstract fun handleStartProgressEvent(event: MotionEvent)
}