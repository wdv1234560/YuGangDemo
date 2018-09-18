package com.jiax.yugang.ui.animator

import android.animation.TypeEvaluator
import android.renderscript.Sampler

/**
 * date     2018/9/18 14:23
 * author   caojiaxu
 * desc
 */
class MyEvaluator:TypeEvaluator<ValueObject> {
    override fun evaluate(fraction: Float, startValue: ValueObject?, endValue: ValueObject?): ValueObject {
//        result = x0 + t * (v1 - v0)
        var alphaValue:Float = startValue?.alphaValue!! +fraction*(endValue?.alphaValue!!- startValue?.alphaValue!!)
        var scaleValue:Float = startValue?.scaleValue!! + fraction*(endValue?.scaleValue!! - startValue?.scaleValue!!)
        return ValueObject(alphaValue,scaleValue)
    }
}