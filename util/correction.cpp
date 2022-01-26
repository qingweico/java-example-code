#include <stdio.h>
// Modify the value of a variable through a function pointer
void *f(int *p){
    p = (int *) 0x1234;
    return nullptr;
}
void f(int **p){
    *p = (int*) 0x1234;
}
void f0(int* p){
    *p = 12;
}
int main(){
    int i = 10;
    int *p = &i;
    printf("%p\n", p);
    f(p);
    printf("%p\n", p);

    f(&p);
    printf("%p\n", p);

    f0(&i);
    printf("%d", i);
    return 0;
}