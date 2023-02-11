package oak.newfeature.impl;

import oak.newfeature.Returned;
import oak.newfeature.crypto.Digest;

/**
 * @author zqw
 * @date 2023/1/15
 */
public class Sha512  implements Digest {
    static final Returned.ReturnValue<Digest> RETURNED_SHA512 = null;

    private Sha512() {

    }

    @Override
    public void digest(byte[] message) {
        System.out.println(new String(message));
    }
}
