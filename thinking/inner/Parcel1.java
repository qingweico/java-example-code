package thinking.inner;


/**
 * @author:周庆伟
 * @date: 2021/1/29
 */
public class Parcel1 {
    class Contents {
        private int i;

        private int value() {
            return i;
        }
    }

    class Destination {
        private final String label;

        Destination(String whereTo) {
            this.label = whereTo;
        }

        String readLabel() {
            return label;
        }
    }

    public void ship(String dest) {
        Contents c = new Contents();
        Destination d = new Destination(dest);
        System.out.println(c.value());
        System.out.println(d.readLabel());
    }

    public static void main(String[] args) {
        Parcel1 p = new Parcel1();
        p.ship("Tasmania");
    }
}
