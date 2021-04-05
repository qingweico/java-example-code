package thinking.concurrency.atom;

/**
 * Atomic operation: Assignment and return operations to values in the domain are usually atomic.
 *
 * Atomicity applies to all basic types except long and double, it is guaranteed that
 * they will operate on memory as non-separable (atomic) operations.
 * @author:qiming
 * @date: 2021/1/19
 */
public class Atomicity {
    //  7: putfield      #2                  // Field i:I
    int i = 1;

    //  2: getfield      #2                  // Field i:I
    //  7: putfield      #2                  // Field i:I
    void f1() {
        i++;
    }

    //  2: getfield      #2                  // Field i:I
    //  7: putfield      #2                  // Field i:I
    void f2() {
        i += 3;
    }


    // javac Atomicity.java
    // cd ../../../
    // javap -c thinking.concurrency.atom.Atomicity
}
