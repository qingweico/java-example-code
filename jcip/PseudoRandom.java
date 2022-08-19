package jcip;

/**
 * Pseudo random integer
 * @author zqw
 * @date 2021/3/30
 */
class PseudoRandom {
    int calculateNext(int prev) {
        prev ^= prev << 6;
        prev ^= prev >>> 21;
        prev ^= (prev << 7);
        return prev;
    }
}