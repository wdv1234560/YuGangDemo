package com.jiax.yugang.widget

import android.content.Context
import android.graphics.PointF
import android.view.MotionEvent
import androidx.core.view.MotionEventCompat.getPointerCount



/**
 * date     2018/7/11 13:47
 * author   caojiaxu
 * desc
 */
class MoveGestureDetector(context: Context) : BaseGestureDetector(context) {

    var mCurrentPointer: PointF? = null
    var mPrePointer: PointF? = null

    //仅仅为了减少内存
    var mDeltaPointF = PointF()
    //用于记录最终结果，并返回
    var mExtenalPointer = PointF()

    var mListener: OnMoveGestureListener? = null

    constructor(context: Context, listener: OnMoveGestureListener) : this(context) {
        mListener = listener
    }

    override fun handleInProgressEvent(event: MotionEvent) {
        var actionCode = event.action and MotionEvent.ACTION_MASK
        when (actionCode) {
            MotionEvent.ACTION_CANCEL or MotionEvent.ACTION_UP -> {
                mListener?.onMoveEnd(this)
                resetState()
            }

            MotionEvent.ACTION_MOVE -> {
                updateStateByEvent(event)
                var update = mListener?.onMove(this)
                if (update!!) {
                    mPreMotionEvent?.recycle()
                    //创建一个新的MotionEvent，从现有的MotionEvent复制。
                    mPreMotionEvent = MotionEvent.obtain(event)
                }
            }

        }
    }

    override fun handleStartProgressEvent(event: MotionEvent) {

        var actionCode = event.action and MotionEvent.ACTION_MASK
        when(actionCode){
            MotionEvent.ACTION_DOWN->{
                resetState()
                mPreMotionEvent = MotionEvent.obtain(event)
                updateStateByEvent(event)
            }
            MotionEvent.ACTION_MOVE->{
                mGestrueInProcess = mListener?.onMoveBegin(this)!!
            }
        }
    }

    protected fun updateStateByEvent(event: MotionEvent) {
        val prev=mPreMotionEvent
        mPrePointer = caculateFocalPointer(prev)
        mCurrentPointer = caculateFocalPointer(event)

        var skipThisMoveEvent = prev?.pointerCount!=event.pointerCount
//        ？：表示当前是否对象可以为空
//        ！！： 表示当前对象不为空的情况下执行
        //minus()：减法运算
        mExtenalPointer.x = if(skipThisMoveEvent) 0f else mCurrentPointer!!.x.minus(mPrePointer!!.x)
        mExtenalPointer.y = if(skipThisMoveEvent) 0f else mCurrentPointer!!.y.minus(mPrePointer!!.y)

    }

    /**
     *根据event计算多指中心点
     *  */
    private fun caculateFocalPointer(event: MotionEvent?): PointF? {
        val count = event!!.pointerCount
        var x = 0f
        var y = 0f
        for (i in 0 until count) {
            x += event.getX(i)
            y += event.getY(i)
        }

        x /= count.toFloat()
        y /= count.toFloat()

        return PointF(x, y)
    }

    fun getMoveX():Float{
        return mExtenalPointer.x
    }

    fun getMoveY():Float{
        return mExtenalPointer.y
    }
    interface OnMoveGestureListener {

        fun onMoveBegin(detector: MoveGestureDetector): Boolean
        fun onMove(detector: MoveGestureDetector): Boolean
        fun onMoveEnd(detector: MoveGestureDetector)
    }


}

