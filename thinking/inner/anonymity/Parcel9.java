package thinking.inner.anonymity;

import thinking.inner.Destination;

/**
 * An anonymous inner class that preforms initialization.
 * A briefer version of Parcel5.java.
 *
 * @author:qiming
 * @date: 2021/1/31
 */
public class Parcel9 {

    public Destination destination(final String dest) {
        return new Destination() {
            private final String label = dest;

            @Override
            public String readLabel() {
                return label;
            }
        };
    }

    public static void main(String[] args) {
        Parcel9 p = new Parcel9();
        Destination d = p.destination("Tasmania");
        System.out.println(d.readLabel());
    }
}
