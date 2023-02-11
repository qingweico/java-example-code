package oak.newfeature.impl;

import oak.newfeature.Returned;
import oak.newfeature.crypto.Digest;


/**
 * @author zqw
 * @date 2023/1/15
 */

/*不希望应用程序直接调用实现的子类, 也不希望应用程序直接访问这个 Java 包 使用包级访问权限*/


final class Sha256 implements Digest {
    static final Returned.ReturnValue<Digest> RETURNED_SHA256 = null;

    private Sha256() {

    }

    @Override
    public void digest(byte[] message) {
        System.out.println(new String(message));
    }
}
