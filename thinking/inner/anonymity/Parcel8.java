package thinking.inner.anonymity;


/**
 * Calling base-class constructor
 *
 * @author:qiming
 * @date: 2021/1/31
 */
public class Parcel8 {
    public Wrapping wrapping(int x) {

        // Base constructor call
        return new Wrapping(x) { // pass constructor argument
            public int value() {
                return super.value() * 2;
            }
        }; // Semicolon required
    }

    public static void main(String[] args) {
        Parcel8 p = new Parcel8();
        Wrapping w = p.wrapping(10);
        System.out.println(w.value());
    }
}
