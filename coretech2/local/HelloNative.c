##include "HelloNative.h"
#include <stdio.h>
JNIEXPORT void JNIEXPORT void JNICALL Java_coretech2_local_HelloNative_greeting
                 (JNIEnv * env, jclass cl) {
                 printf("Hello Native World!\n");
                 }