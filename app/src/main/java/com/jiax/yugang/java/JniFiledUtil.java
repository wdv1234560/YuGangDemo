package com.jiax.yugang.java;

/**
 * date     2018/8/9 15:35
 * author   caojiaxu
 * desc
 */
public class JniFiledUtil {
    private void callback() {
        System.out.println("In Java");
    }
    private static void callback2() {
        System.out.println("In Java static");
    }

    public static native void accessField();
    public native void nativeMethod();
    public static native void nativeMethod2();
    static {
        System.loadLibrary("jni-filed");
    }
}
