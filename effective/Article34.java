package effective;

import lombok.Getter;

import java.util.Optional;
import java.util.Scanner;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * 用enum代替int常量
 *
 * @author zqw
 * @date 2020/10/23
 */
// Java supports two special-purpose reference types,
// One is a class, called an enumerated type,
// One is interfaces(@interface annotation), called annotation types.

class Article34 {
    /**
     * int enum pattern
     * disadvantages: There is no type safety and no description at all.
     */
    public static final int APPLE_FUJI = 1;
    // Int enumeration is a compile-time constant. if the value associated with the
    // int enumeration constant changes, the client must compile, otherwise the
    // program will still run but its behavior will no longer be accurate.

    public static void main(String[] args) {
        System.out.println(APPLE_FUJI);
    }
}

/**
 * Enumeration in Java is essentially an int value
 */
@Getter
enum Planet {
    // 枚举常量
    /**
     * 水星
     */
    MERCURY(3.302e+23, 2.439e6),
    /**
     * 金星
     */
    VENUS(4.869e+24, 6.052e6),
    /**
     * 地球
     */
    EARTH(5.975e+24, 6.378e6),
    /**
     * 火星
     */
    MARS(6.419e+23, 3.393e6),
    /**
     * 木星
     */
    JUPITER(1.899e+27, 7.149e7),
    /**
     * 土星
     */
    SATURN(5.685e+26, 6.027e7),
    /**
     * 天王星
     */
    URANUS(8.683e+25, 2.556e7),
    /**
     * 海王星
     */
    NEPTUNE(1.024e+26, 2.477e7);
    // 实例域
    /**
     * 行星质量
     */
    private final double mass;
    /**
     * 行星半径
     */
    private final double radius;
    /**
     * 重力加速度
     */
    private final double surfaceGravity;
    /**
     * 卡文迪许常量
     */
    private static final double G = 6.67300E-11;

    /**
     * A constructor that takes data and stores it in the instance domain
     * with a default modifier of private.
     */
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        this.surfaceGravity = G * mass / (radius * radius);
    }

    public double getSurfaceWeight(double mass) {
        return mass * surfaceGravity;
    }

    public static void main(String[] args) {
        // Shows the weight of an object on all eight planets based on its weight on Earth.
        Scanner input = new Scanner(System.in);
        double earthWeight = input.nextDouble();
        double mass = earthWeight / Planet.EARTH.surfaceGravity;
        // Values() returns an array of its values in declarative order
        for (Planet planet : Planet.values()) {
            System.out.printf("Weight on %s is %f%n", planet, planet.getSurfaceWeight(mass));
        }
    }

}

/**
 * Enum type that switches on its own value - questionable
 */
enum BaseOperations {
    /**
     * ~
     */
    PLUS, MINUS, TIMES, DIVIDE;

    /**
     * Do the arithmetic operation represented by this constant
     */
    public double apply(double x, double y) {
        return switch (this) {
            case PLUS -> x + y;
            case MINUS -> x - y;
            case TIMES -> x * y;
            case DIVIDE -> x / y;
        };
        // It cannot compile without a throw statement
    }

    public static void main(String[] args) {
        double res = BaseOperations.TIMES.apply(2, 3);
        System.out.println(res);
    }
}

/**
 * Enum type with constant-specific method implements
 */
enum Operations {
    // There is a better way to associate different behaviors with each enumeration
    // constant: Declare an abstract apply method in an enumeration type and override
    // the abstract apply method for each constant with a concrete method in the body
    // of a constant-specific class, which is called a constant-specific method
    // implementation.
    PLUS {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    /**
     * An abstract method in an enumerated type must be overridden by a concrete
     * method in all of its constants.
     */
    public abstract double apply(double x, double y);

}

/**
 * Enum type with constant-specific class bodies and data
 */
enum Operation {
    /**
     * ~
     */
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    }, MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    }, TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    }, DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    public abstract double apply(double x, double y);

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "symbol='" + symbol + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double x = input.nextDouble();
        double y = input.nextDouble();
        for (Operation op : Operation.values()) {
            System.out.printf("%f %s %f = %f%n", x, op.symbol, y, op.apply(x, y));
        }
        System.out.println(fromString("+"));
    }

    /**
     * Implementing a fromString method on an enum type
     */
    private static final Map<String, Operation> STRING_TO_ENUM =
            Stream.of(values()).collect(toMap(Object::toString, e -> e));

    /**
     * Returns Operation for string, if any
     */
    public static Optional<Operation> fromString(String symbol) {
        return Optional.ofNullable(STRING_TO_ENUM.get(symbol));
    }
}
