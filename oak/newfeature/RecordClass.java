package oak.newfeature;

/**
 * 档案类 : 用来表示不可变数据的透明载体
 *
 * @author zqw
 * @date 2022/12/20
 */
class RecordClass {
    public static void main(String[] args) {
        Circle c1 = new Circle(2.0);
        // Java 档案类内置了不可变数据的读取方法
        // 同时还包括构造方法 hashCode equals toString 方法的缺省实现
        System.out.println(c1.radius());
        Circle c2 = new Circle(2.0);
        // true
        System.out.println(c1.equals(c2));
    }
}

record Circle(double radius) {
    public Circle {
        if (radius < 0) {
            throw new IllegalArgumentException("The radius of a circle cannot be negative [" + radius + "]");
        }
    }

    public double area() {
        return radius * radius * Math.acos(-1.0);
    }

    public static void main(String[] args) {
        var circle = new Circle(2.18);
        System.out.println(circle.radius());
        System.out.println(circle.area());
    }
}