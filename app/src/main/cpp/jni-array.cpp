//
// Created by admin on 2018/8/7.
//

#include <jni.h>
/*JNIEXPORT和JNICALL这两个宏（被定义在jni.h）确保这个函数在本地库外可见，并且C编译器会进行正确的调用转换。*/
extern "C"
JNIEXPORT jint JNICALL
Java_com_jiax_yugang_java_SimpleJniUtil_sumArray(JNIEnv *env, jclass type, jintArray arr_) {
    /*jint *arr = env->GetIntArrayElements(arr_, NULL);
    env->ReleaseIntArrayElements(arr_, arr, 0);*/
    jsize len = env->GetArrayLength(arr_);
    int buf[len];
    int sum = 0;
    //将基本类型数组某一区域复制到缓冲区中的一组函数。
    env->GetIntArrayRegion(arr_, 0, len, buf);
    for (int i = 0; i < len; i++) {
        sum += buf[i];
    }

    return sum;
}


extern "C"
JNIEXPORT jintArray JNICALL
Java_com_jiax_yugang_java_SimpleJniUtil_arrayAddTen(JNIEnv *env, jclass type, jintArray arr_) {

    jsize len = env->GetArrayLength(arr_);

    jint *arr = env->GetIntArrayElements(arr_, NULL);
    for (int i = 0; i < len; ++i) {
        //获取数组元素的两种表达方式
        *(arr + i) += 10;
        arr[i] += 1;
    }
    env->ReleaseIntArrayElements(arr_, arr, 0);
    return arr_;
}

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_jiax_yugang_java_SimpleJniUtil_init2DArray(JNIEnv *env, jclass type, jint size) {
    jobjectArray result;
    jclass intArrCls = env->FindClass("[I");
    if (intArrCls == NULL) {
        return NULL;
    }

    result = env->NewObjectArray(size, intArrCls, NULL);
    if (result == NULL) {
        return NULL;
    }
    for (int i = 0; i < size; ++i) {
        jint temp[256];//定义一个足够大的数组
        jintArray iarr = env->NewIntArray(size);
        if (iarr == NULL) {
            return NULL;
        }

        for (int j = 0; j < size; ++j) {
            temp[j] = i + j;
        }

        //把tmp[]缓冲区中的内容复制到新分配的一维数组中
        env->SetIntArrayRegion(iarr, 0, size, temp);
        //把一维数组设置到对象数组i的地址中
        env->SetObjectArrayElement(result, i, iarr);
        //循环最后调用DeleteLocalRef，确保JVM释放掉iarr这个JNI引用
        env->DeleteLocalRef(iarr);
    }

    return result;
}