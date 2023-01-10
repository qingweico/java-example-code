package oak.newfeature;


/**
 * -------------------- 类型匹配 --------------------
 * type matching : preview @since JDK 14 JDK 15, official @since JDK 16
 *
 * @author zqw
 * @date 2023/1/1
 */
class TypeMatching {
    public static void main(String[] args) {
        Shape.Rectangle rectangle = new Shape.Rectangle("Rectangle");
        rectangle.setWidth(10);
        rectangle.setLength(10);
        System.out.println(isSquare(rectangle));
        System.out.println(isSquare0(rectangle));
    }

    static boolean isSquare(Shape shape) {
        // 类型匹配
        if (shape instanceof Shape.Rectangle rect) {
            return (rect.getLength() == rect.getWidth());
        }
        return (shape instanceof Shape.Square);


    }
    /*匹配变量的作用域*/
    // 在类型匹配中,Java 编译器不会允许使用没有赋值的匹配变量
    // 即在一个范围里,如果编译器能够确定匹配变量已经被赋值了,
    // 那么它就可以在这个范围内使用;如果编译器不能够确定匹配
    // 变量是否被赋值,或者确定没有被赋值,那么它就不能在这个
    // 范围内使用


    static boolean isSquare0(Shape shape) {
        // 如果变量 shape 没有匹配到 Rectangle 类型,则 rect
        // 变量则不会在if块中生效
        if (!(shape instanceof Shape.Rectangle rect)) {
            return (shape instanceof Shape.Square);
        }
        return (rect.getLength() == rect.getWidth());
    }
}
// 与使用类型转换运算符的代码相比,使用实例匹配代码可以提高性能