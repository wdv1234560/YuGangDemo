package com.jiax.yugang.java;

/**
 * @date     2018/7/13 11:20
 * @author   caojiaxu
 * @desc
 */
public class SimpleJniUtil {
    public static native int doubleData(int data);
    public static native int sumArray(int[] arr);
    public static native int[] arrayAddTen(int[] arr);
    public static native int[][] init2DArray(int size);
    public static native String getText();

    static {
        System.loadLibrary("jni-array");
    }

}
