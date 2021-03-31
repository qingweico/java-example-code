package jcip;

import annotation.GuardedBy;
import annotation.ThreadSafe;

/**
 * @author:qiming
 * @date: 2021/3/28
 */
@ThreadSafe
public class SynchronizedInteger {
    @GuardedBy("this")
    private int value;
    public synchronized int get() {return value;}
    public synchronized void set(int value) {
        this.value = value;
    }
    public synchronized void increment() {
        value++;
    }
}
