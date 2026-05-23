package oak.rnd;

import java.security.SecureRandom;

/**
 * 在服务端应用中, 应极力避免使用 {@link SecureRandom#getInstanceStrong() }方法获取随机数实例
 * 内部实现是 the real blocking random source 会阻塞线程, 使用普通的全局单例 {@link SecureRandom} 替代
 *
 * @author zqw
 * @date 2026/5/22
 */
class SecureRnd {
    private static final SecureRandom SECURE_RANDOM =
            new SecureRandom();

    public static void main(String[] args) {
        System.out.println(SECURE_RANDOM.nextFloat());
    }
}
