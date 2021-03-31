package thinking.inner.anonymity;

import thinking.inner.Contents;

/**
 * Returning a instance of an anonymous inner class.
 *
 * @author:周庆伟
 * @date: 2021/1/31
 */
public class Parcel7 {
    public Contents contents() {
        // Create an object that extends from the anonymous inner class of
        // Contents, the reference returned by the new expression is
        // automatically turned up to the reference of Contents.
        return new Contents() {
            private int i = 11;
            public int value() {return i;}
        };
    }

    public static void main(String[] args) {
        Parcel7 p = new Parcel7();
        Contents c = p.contents();
    }
}
