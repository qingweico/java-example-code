#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
int count = 0;

// Defined a spin lock
pthread_spinlock_t spin_lock;
void increment(void);

int main() {
	int ret;
	// initialize spin lock
	pthread_spin_init(&spin_lock, 0);
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
	for(long i = 0; i <= 9999; i++) {

	 pthread_spin_lock(&spin_lock);

		tmp = count;
		tmp = tmp + 1;
		count = tmp;

	  pthread_spin_unlock(&spin_lock);
	}

}
