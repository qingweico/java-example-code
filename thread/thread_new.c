#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include "thread_CustomThread.h"

pthread_t pid;

void* pthread_entity(void* args) {
      printf("a thread...\n");
}


JNIEXPORT void JNICALL Java_thread_CustomThread_start0(JNIEnv *env, jobject c1)
{

    pthread_create(&pid, NULL, pthread_entity, NULL);
    usleep(50);
    printf("main\n");
}
