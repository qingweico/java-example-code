#include <unistd.h>
#include <stdlib.h>
int main() {
    write(1, "hello, world\n", 13);
    _exit(0);
}