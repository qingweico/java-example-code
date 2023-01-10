#include <stdio.h>
#include "coretech2_local_HelloNative.h"
// 实例方法
JNIEXPORT void JNICALL Java_coretech2_local_HelloNative_instance(JNIEnv *env, jobject thisObject)
{
    printf("HelloNative_instance!\n");
    // 在 JNI 中访问字段类似于反射 API
    // 首先需要通过类实例获得FieldID,然后再通过FieldID获得某个实例中该字段的值
    jclass cls = (*env)->GetObjectClass(env, thisObject);
    jfieldID fieldID = (*env)->GetFieldID(env, cls, "i", "I");
    jint value = (*env)->GetIntField(env, thisObject, fieldID);
    printf("m = %d\n", value);
}
// 无参的静态方法
JNIEXPORT void JNICALL Java_coretech2_local_HelloNative_greeting(JNIEnv *env, jclass cl)
{
    printf("HelloNative_greeting!\n");
}
// 带有一个long型参数的静态方法
JNIEXPORT void JNICALL Java_coretech2_local_HelloNative_greeting__J(JNIEnv *env, jclass cl, jlong val)
{
    printf("HelloNative_greeting__J!\n");
    printf("param = %d\n", val);
}