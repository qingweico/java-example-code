package oak.newfeature;

import oak.newfeature.crypto.Digest;

/**
 * @author zqw
 * @date 2023/1/15
 */
sealed public interface Returned<T> {

    record ReturnValue<T>(T rv) implements Returned<T> {
        @Override
        public String toString() {
            return "ReturnValue : " + rv;
        }
    }

    record Undefined<T>(String tip) implements Returned<T> {
        @Override
        public String toString() {
            return tip;
        }
    }

    Returned<Digest> UNDEFINED = new Returned.Undefined<>("Undefined");


    record ErrorCode<T>(int code) implements Returned<T> {
        @Override
        public String toString() {
            return "ErrorCode : " + code;
        }
    }
}
