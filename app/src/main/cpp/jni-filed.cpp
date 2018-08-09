#include <jni.h>
#include <stdio.h>
#include <android/log.h>
#define  LOG    "JavaCallCDemoLog" // 这个是自定义的LOG的标识
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG,__VA_ARGS__) // 定义LOGF类型
//
// Created by admin on 2018/8/9.
//
extern "C"
JNIEXPORT void JNICALL
Java_com_jiax_yugang_java_JniFiledUtil_accessField(JNIEnv *env, jclass obj) {

// TODO

}

/*jclass和jobject的迷惑
        第一次使用JNI，实例引用（jobject）和类引用(jclass)让人觉得很困惑。
实例引用与一个数组和java.lang.Object类或它的子类的实例对应。类引用与java.lang.Class实例对应，它代表着类的类型。
一个操作如GetFieldID,需要参数jclass，是一个类操作，因为它从一个类中获得field的描述。与此相反，GetIntField需要参数jobject，这是一个实例操作，因为它从这个实例中获得这个field的值。在所有的JNI方法中jobject和实例操作的结合和jclass和类操作的结合保持一致。所以是很容易记住类操作与实例操作的不同的。



随后，普及下静态方法和实例方法的区别：

静态方法与静态变量一样，属于类本身，而不属于那个类的一个对象。调用一个被定义为static的方法，可以通过在它前面加上这个类的名称，也可以像调用非静态方法一样通过类对象调用。
实例方法必须通过类的实例来使用。实例方法可以使用类的非静态成员，也可以使用类的静态成员。
类的静态方法，静态变量是在类装载的时候装载的。但是要特别注意，类的静态变量是该类的对象所共有的，即是所有对象共享变量。所以建议尽量少用静态变量。尽量在静态方法中使用内部变量。

其中static关键字即表示静态的。声明静态方法的语法如下：
<访问修饰符>static返回类型 方法名(参数列表)
        {//方法体}

                静态方法与实例方法唯一不同的，就是静态方法在返回类型前加static关键字。静态方法的调用有两种途径：
        （1）通过类的实例对象去调用
        调用格式为： 对象名.方法名
        (2) 通过类名直接调用
        调用格式为： 类名::方法名*/
extern "C" JNIEXPORT void JNICALL
Java_com_jiax_yugang_java_JniFiledUtil_nativeMethod(JNIEnv *env, jobject obj) {

// TODO
// TODO
    jclass jcls = env->GetObjectClass(obj);
    jmethodID mid = env->GetMethodID(jcls, "callback", "()V");
    if (mid == NULL) {
        return;
    }
    LOGD("In C");
    env->CallVoidMethod(obj, mid);
}

/*
 * jclass 只能调java中的静态方法，否则报
 * JNI DETECTED ERROR IN APPLICATION: calling non-static method void com.jiax.yugang.java.JniFiledUtil.callback()
 * */
extern "C"
JNIEXPORT void JNICALL
Java_com_jiax_yugang_java_JniFiledUtil_nativeMethod2(JNIEnv *env, jclass type) {

    jmethodID mid = env->GetStaticMethodID(type,"callback2","()V");
    if(mid==NULL){
        return;
    }
    LOGD("IN C static");
    env->CallStaticVoidMethod(type,mid);
}