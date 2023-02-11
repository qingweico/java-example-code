package oak.newfeature.crypto;

import oak.newfeature.Returned;

/**
 * @author zqw
 * @date 2023/1/15
 */
public interface DigestManager {
    /**
     * 创建实现某种算法
     *
     * @param algorithm 具体实现的算法名称
     * @return {@code Returned<Digest>}
     */
    Returned<Digest> create(String algorithm);
}
