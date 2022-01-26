#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
void init(struct Arr *arr, int len);
bool isEmpty(struct Arr *arr);
bool isFull(struct Arr *arr);
void print(struct Arr *arr);
bool append(struct Arr *arr, int val);
bool insert(struct Arr *arr, int pos, int val);
bool delete_by_pos(struct Arr *arr, int pos);
struct Arr {
    // The number of valid elements in the array
    int ctn;
    // The length of an array
    int len;
    // The first address of the array
    int* base;
};
int main() {
    struct Arr arr;
    init(&arr, 6);
    append(&arr, 1);
    append(&arr, 2);
    append(&arr, 3);
    append(&arr, 4);
    append(&arr, 5);
    print(&arr);
    delete_by_pos(&arr, 4);
    print(&arr);

}
void init(struct Arr* arr, int len) {
    arr -> base = (int *) malloc(sizeof(int) * len);
    if(arr->base == NULL) {
        printf("Memory allocation failure!\n");
        exit(-1);
    }
    arr -> len = len;
    arr -> ctn = 0;

}
void print(struct Arr *arr) {
    if(isEmpty(arr)){
        printf("array is empty\n");
        return;
    }
    for(int i = 0;i < arr -> ctn;i++){
        printf("%d ", arr -> base[i]);
    }
    printf("\n");

}
bool isFull(struct Arr *arr) {
    return arr -> ctn == arr -> len;
}
bool isEmpty(struct Arr *arr) {
    return arr -> ctn == 0;
}
bool append(struct Arr *arr, int val) {
    if(isFull(arr)){
        printf("array has full!\n");
        return false;
    }
    arr -> base[arr -> ctn++] = val;
    return true;
}
bool insert(struct Arr *arr, int pos, int val) {
    if(isFull(arr)){
        printf("array has full!\n");
        return false;
    }
    if(pos < 1 || pos > arr -> ctn + 1){
        printf("invalid pos!\n");
        return false;
    }
    for(int i = arr -> ctn - 1; i >= pos - 1;i--){
        arr -> base[i + 1] = arr -> base[i]; 
    }
    arr -> base[pos - 1] = val;
    arr -> ctn++;
    return true;
}
bool delete_by_pos(struct Arr *arr, int pos){
    if(isEmpty(arr)){
        printf("array is Empty!\n");
        return false;
    }
    if(pos < 1 || pos > arr -> ctn + 1) {
        printf("invalid pos!\n");
        return false;
    }
    for(int i = pos; i < arr -> ctn;i++){
        arr -> base[i - 1] = arr -> base[i];
    }
    arr -> ctn--;
    return true;
}