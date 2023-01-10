package oak.newfeature;

import java.util.Objects;
import java.util.ServiceLoader;

/**
 * -------------------- 模块化应用程序 --------------------
 *
 * @author zqw
 * @date 2023/1/7
 */
class ModularApplication {
    public static void main(String[] args) {

    }
}

interface Digest {
    byte[] digest(byte[] message);

    static Returned<Digest> of(String algorithm) {
        ServiceLoader<DigestManager> serviceLoader =
                ServiceLoader.load(DigestManager.class);
        for (DigestManager cryptoManager : serviceLoader) {
            Returned<Digest> rt = cryptoManager.create(algorithm);
            if (Objects.requireNonNull(rt) instanceof Returned.ReturnValue<Digest> rv) {
                return rv;
            }
            throw new IllegalStateException("Unexpected value: " + rt);
        }
        return null;
    }
}

interface DigestManager {
    Returned<Digest> create(String algorithm);
}