package thinking.inner.anonymity;

import thinking.inner.Contents;

/**
 * Expanded version of Parcel7.java
 *
 * @author:qiming
 * @date: 2021/1/31
 */
public class Parcel7b {
    static class MyContents implements Contents {
        private int i = 11;
        public int value() {
            return i;
        }
    }
    public Contents contents() {
        return new MyContents();
    }

    public static void main(String[] args) {
        Parcel7b p = new Parcel7b();
        Contents c = p.contents();
    }
}
