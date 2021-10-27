package effective;

import java.awt.*;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static util.Print.print;

/**
 * 覆盖equals时请遵守通用约定
 *
 * @author:qiming
 * @date: 2020/11/1
 */

/*Object的规范
        x != null
        1>:  自反性 x.equals(x) return true
        2>:  对称性 y.equals(x) return true; x.equals(y) return true
        3>:  传递性 x.equals(y) return true, y.equals(z), return true; x.equals(z) return true
        4>:  一致性 Calling x.equals(y) multiple times always returns consistent results as
                   long as the information used in the object has not been modified.
        5>:  非空性 x.equals(null) return false;
 */

// Broken - violates symmetry
final class CaseInsensitiveString {
    private final String s;

    @Override
    public boolean equals(Object o) {

//      Solved! Remove code that attempts to inter-operate with String.
//        return o instanceof CaseInsensitiveString &&
//                s.equalsIgnoreCase(((CaseInsensitiveString) o).s);


        if (o instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        }
        if (o instanceof String) {
            return s.equalsIgnoreCase((String) o);
        }
        return false;

    }
    // Remainder omitted

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);

    }

    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        System.out.println(cis.equals(s)); // true
        System.out.println(s.equals(cis)); // false
    }
}

// Using getClass() instead of instanceof in the equals method extends the instantiable class
// and adds new components while preserving the equals component.
class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        // Broken
        if (!(o instanceof Point)) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;

//        Violates Liskov substitution principle
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Point point = (Point) o;
//        return x == point.x &&
//                y == point.y;
    }

}

class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
//        Good
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        ColorPoint that = (ColorPoint) o;
//        return Objects.equals(color, that.color);

//        Broken - violates symmetry
//        if (!(o instanceof ColorPoint)) return false;
//        return super.equals(o) && ((ColorPoint) o).color == color;

//        Broken - violates transitivity!
        if (!(o instanceof Point)) {
            return false;
        }
        // If o is a normal Point, do a color-blind comparision.
        if (!(o instanceof ColorPoint)) {
            return o.equals(this);
        }
        // The o is a ColorPoint, do a full comparision.
        return super.equals(o) && ((ColorPoint) o).color == color;
    }

    public static void main(String[] args) {
        ColorPoint cp = new ColorPoint(1, 2, Color.GREEN);
        Point p = new Point(1, 2);
        print(p.equals(cp));
        print(cp.equals(p));
        ColorPoint p2 = new ColorPoint(1, 2, Color.RED);
        print(cp.equals(p)); // true
        print(p.equals(p2)); // true
        print(cp.equals(p2));// false

    }
}

class SmellPoint extends Point {
    public enum Smell {
        TASTY, SALTY
    }

    private final Smell smell;

    public SmellPoint(int x, int y, Smell smell) {
        super(x, y);
        this.smell = smell;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        // If o is a normal Point, do a smell-blind comparision.
        if (!(o instanceof SmellPoint)) {
            return o.equals(this);
        }
        // The o is a SmellPoint, do a full comparision.
        return super.equals(o) && ((SmellPoint) o).smell == smell;
    }

    public static void main(String[] args) {
        ColorPoint myColorPoint = new ColorPoint(1, 2, Color.RED);
        SmellPoint mySmellPoint = new SmellPoint(1, 2, Smell.SALTY);
        // StackOverflowError
        System.out.println(myColorPoint.equals(mySmellPoint));
    }

}

class CounterPoint extends Point {
    private static final AtomicInteger counter = new AtomicInteger();

    public CounterPoint(int x, int y) {
        super(x, y);
        counter.incrementAndGet();
    }

    public static int numberCreated() {
        return counter.get();
    }
}

public class Article10 {

    // Initialize unitCircle to contain all Points on the unit circle.
    private static final Set<Point> unitCircle = Set.of(
            new Point(1, 0), new Point(0, 1),
            new Point(-1, 0), new Point(0, -1)
    );

    public static boolean onUnitCircle(Point p) {
        return unitCircle.contains(p);
    }

    public static void main(String[] args) {
        System.out.println(onUnitCircle(new CounterPoint(1, 0)));
        System.out.println(CounterPoint.numberCreated());
    }

}
