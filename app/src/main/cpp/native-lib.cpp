#include <jni.h>
#include <string>

char *_JString2CStr(JNIEnv *pEnv, jstring pJstring);

extern "C" JNIEXPORT jstring JNICALL
Java_com_jiax_yugang_jni_JniDemoActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jint JNICALL
Java_com_jiax_yugang_jni_JniDemoActivity_integerFromJNI(JNIEnv *env, jobject) {
    std::int32_t hello = 32;
    return hello;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_jiax_yugang_jni_JniDemoActivity_sayHello(JNIEnv *env, jobject obj, jstring text) {
    char *fromJava = _JString2CStr(env, text);
    char *fromC = "abcd";
    strcat(fromJava, fromC);
    return env->NewStringUTF(fromJava);
}

/**
    * 将数组中的每个元素增加10
    *
    * @param intArray
    * @return
    */
extern "C" JNIEXPORT jintArray JNICALL
Java_com_jiax_yugang_jni_JniDemoActivity_arrayAddTen(JNIEnv *env, jobject obj, jintArray arr) {

    //1.得到数组长度
    jsize lenght = env->GetArrayLength(arr);

    //2.获取数组指针,得到数组起始地址
    jint *array = env->GetIntArrayElements(arr, NULL);
    int i;
    for (i = 0; i < lenght; i++) {
        printf("arr1==%d\n", *(array + i));
        array[i] += 10;
        printf("arr2==%d\n", *(array + i));
    }
    //4.释放资源,不释放得到的还是初始的值
    env->ReleaseIntArrayElements(arr, array, 0);
    return arr;
}

/**
    * 生成一个数组
    *
    * @param intArray
    * @return
    */
extern "C" JNIEXPORT jintArray JNICALL
Java_com_jiax_yugang_jni_JniDemoActivity_getArray(JNIEnv *env, jobject obj, jint len) {

    //1.新建长度len数组
    jintArray jarr = env->NewIntArray(len);
    //2.获取数组指针
    jint *arr = env->GetIntArrayElements(jarr, NULL);
    //3.赋值
    int i = 0;
    for (; i < len; i++) {
        arr[i] = i;
    }
    //4.释放资源
    env->ReleaseIntArrayElements(jarr, arr, 0);
    //5.返回数组
    return jarr;
}

/**
 *  应用: 检查密码是否正确, 如果正确返回200, 否则返回400
 "123456"
 * @param pwd
 * @return
 */
extern "C" JNIEXPORT jboolean JNICALL
Java_com_jiax_yugang_jni_JniDemoActivity_checkPwd(JNIEnv *env, jobject obj, jstring pwd) {

    char *pd = _JString2CStr(env, pwd);
    char *str = "123456";
    int result = strcmp(pd, str);
    if (result == 0) {
        return true;
    }
    return false;
}


/**
 * 工具函数
 * 把一个jstring转换成一个c语言的char*类型
 *
 *  对于***.c
 *  1.jclass test_class = (*env)->GetObjectClass(env, obj);
 *  2.jfieldID id_num = (*env)->GetFieldID(env, test_class, "num", "I");
 *
 *  对于 ***.cpp
 *  1.jclass test_class = env->GetObjectClass(obj);
 *  2.jfieldID id_num = env->GetFieldID(test_class, "num", "I");
 *
 *  在 C 中，
 *  JNI 函数调用由“(*env)->”作前缀，目的是为了取出函数指针所引用的值。
 *  在 C++ 中，
 *  JNIEnv 类拥有处理函数指针查找的内联成员函数。
 *
 *  下面将说明这个细微的差异，其中，这两行代码访问同一函数，但每种语言都有各自的语法。
 *  C 语法：jsize len = (*env)->GetArrayLength(env,array);
 *  C++ 语法：jsize len =env->GetArrayLength(array);
 * */

char *_JString2CStr(JNIEnv *env, jstring jstr) {
    char *rtn;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("GB2312");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (env->CallObjectMethod(jstr, mid, strencode));
    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) (malloc(alen + 1));//"\0"
        memcpy(rtn, ba, alen);
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}
