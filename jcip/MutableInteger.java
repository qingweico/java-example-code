package jcip;

import annotation.NotThreadSafe;

/**
 * @author:qiming
 * @date: 2021/3/28
 */
@NotThreadSafe
class MutableInteger {
    private int value;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}
