package effective;

import java.math.BigInteger;

/**
 * 检查参数的有效性
 *
 * @author zqw
 * @date 2021/4/3
 */
class Article49 {
    /**
     * Return a BigInteger whose value is (this mod m). This method
     * differs from the remainder method in that it always return a
     * non-negative BigInteger
     *
     * @param m the modulus, which must be positive
     * @return this mod m
     * @throws ArithmeticException if m is less than or equal to 0
     */
    public BigInteger mod(BigInteger m) {
        if(m.signum() <= 0) {
            throw new ArithmeticException("Modulus <= 0:" + m);
        }
        return new BigInteger("0");
    }
}
