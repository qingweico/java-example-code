package thinking.inner.anonymity;


/**
 * Calling base-class constructor
 *
 * @author zqw
 * @date 2021/1/31
 */
class Parcel8 {
    public Wrapping wrapping(int x) {

        // Base constructor call

        // pass constructor argument
        return new Wrapping(x) {
            @Override
            public int value() {
                return super.value() * 2;
            }
            // Semicolon required
        };
    }

    public static void main(String[] args) {
        Parcel8 p = new Parcel8();
        Wrapping w = p.wrapping(10);
        System.out.println(w.value());
    }
}
