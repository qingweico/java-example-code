#include <stdio.h>
// a^a = 0
// There is no performance advantage, it's just a mind game
void inplace_swap(int * x, int *y) {
    *y = *x ^ *y;
    *x = *x ^ *y;
    *y = *x ^ *y;
}
void reverse_array(int a[], int cnt) {
    int first, last;
    for(first = 0, last = cnt - 1; first <= last;
     first++, last--) {
         inplace_swap(&a[first], &a[last]);
     }
}
void print(int *arr, int len) {
    for(int i = 0;i < len;i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}
int main() {
    int x = 1;
    int y = 2;
    inplace_swap(&x, &y);
    printf("x = %d, y = %d", x, y);
    int arr[4] = {1, 2, 3, 4};
    int a[5] = {1, 2, 3 ,2, 5};
    reverse_array(arr, sizeof(arr) / sizeof(arr[0]));
    reverse_array(a, sizeof(a) / sizeof(a[0]));
    print(arr, 4);
    print(a, 5);
    int lval = 0xFEDCBA98 << 31;
    printf("%d", lval);
}