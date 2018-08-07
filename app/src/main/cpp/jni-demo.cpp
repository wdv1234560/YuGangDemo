//
// Created by admin on 2018/8/7.
//

#include <jni.h>

extern "C"
JNIEXPORT jint JNICALL
Java_com_jiax_yugang_java_SimpleJniUtil_sumArray(JNIEnv *env, jclass type, jintArray arr_) {
    /*jint *arr = env->GetIntArrayElements(arr_, NULL);
    env->ReleaseIntArrayElements(arr_, arr, 0);*/
    jsize len = env->GetArrayLength(arr_);
    int buf[len];
    int sum =0;
    env->GetIntArrayRegion(arr_,0,len,buf);
    for(int i=0;i<len;i++){
        sum+=buf[i];
    }

    return sum;
}