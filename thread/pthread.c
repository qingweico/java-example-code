#include <stdio.h>
#include <unistd.h>
#include <pthread.h>

pthread_t pid;

void* pthread_entity(void* args) {
   while(1) {
        usleep(100);
        printf("a thread...\n");
    }
}
int main() {

    pthread_create(&pid, NULL, pthread_entity, NULL);
    while(1) {
        usleep(50);
        printf("main\n");
    }

}
// usage: gcc pthread.c -o pthread -pthread
