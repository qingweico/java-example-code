package oak.newfeature.crypto;

import oak.newfeature.Returned;

import java.util.Objects;
import java.util.ServiceLoader;

/**
 * @author zqw
 * @date 2023/1/15
 * @see ServiceLoader 用法
 */
public interface Digest {
    void digest(byte[] message);

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
