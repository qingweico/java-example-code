/*************************************************************************
	> File Name: mutex_lock.c
	> Author: qiming
	> Mail: zqingwei99@gmail.com 
	> Created Time: Sat 26 Jun 2021 02:23:34 PM CST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
int count = 0;
// Create mutex lock
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
void increment(void);

int main() {
	int ret;

	pthread_t t1, t2, t3;
	pthread_create(&t1, NULL, (void *)&increment, NULL);
	pthread_create(&t2, NULL, (void *)&increment, NULL);
	pthread_create(&t3, NULL, (void *)&increment, NULL);

	pthread_join(t1, NULL);
	pthread_join(t1, NULL);
	pthread_join(t3, NULL);

	printf("count = %d\n", count);

	return 0;

}
void increment(void) {

	long tmp;
	for(long i = 0;i <= 9999;i++) {
	// Blocking calls
	 pthread_mutex_lock(&mutex);

		tmp = count;
		tmp = tmp + 1;
		count = tmp;
		
	 // Release mutex lock
	  pthread_mutex_unlock(&mutex);
	}

}
