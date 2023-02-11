package oak.newfeature.impl;

import oak.newfeature.Returned;
import oak.newfeature.crypto.Digest;
import oak.newfeature.crypto.DigestManager;

/**
 * @author zqw
 * @date 2023/1/15
 */

public final class DigestManagerImpl implements DigestManager {
    @Override
    public Returned<Digest> create(String algorithm) {
        return switch (algorithm) {
            case "SHA-256" -> Sha256.RETURNED_SHA256;
            case "SHA-512" -> Sha512.RETURNED_SHA512;
            default -> Returned.UNDEFINED;
        };
    }
}
