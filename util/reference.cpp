#include <iostream>
using namespace std;
// int & --> int * const --> const pointer
void print(int &val) {
    cout << val << endl;
    val = 100;
}

// read-only
void print0(const int &val) {
    cout << val << endl;
    // error
    // val = 133;
}
int main() {
    int a = 10;
    print(a);
    cout << a << endl;

    
    int b = 100;

    // cannot bind non-const lvalue reference of type 'int&'
    // int &ref = 11;
 
    const int& refs = 11;

    cout << refs << endl;
}
