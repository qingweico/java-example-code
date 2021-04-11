#include <iostream>
using namespace std;
template<class T> class Manipulator {
    T obj;
public:
    Manipulator(T x) {obj = x;}
    void manipulate() {obj.f();}
};
class Hasf {
public:
    void f() {
    cout << "HasF::f()" << endl;
    }
};
int main() {
    Hasf hf;
    Manipulator<Hasf> manipulator(hf);
    manipulator.manipulate();
}