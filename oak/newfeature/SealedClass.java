package oak.newfeature;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * -------------------- 封闭类 --------------------
 * sealed class : preview @since JDK 15 JDK 16, official @since JDK 17
 * 出现的背景 : 面向对象中类的无限制的扩展性
 * ********** 继承的安全缺陷 **********
 * 1 : 一个可扩展的类,子类和父类可能会相互影响,从而导致不可预知的行为
 * 2 : 涉及敏感信息的类,增加可扩展性不一定是个优先选项,要尽量避免父类或者子类的影响
 * 限制住可扩展性只有两个方法 : 
 * 1 : 使用私有类
 * 2 : final 修饰符
 * 私有类不是公开接口,只能内部使用;而 final 修饰符彻底放弃了可扩展性;要么全开放,要么全封
 * 闭,可扩展性只能在可能性的两个极端游走;全封闭彻底没有了可扩展性,全开放又面临固有的安全缺陷
 *
 * @author zqw
 * @date 2023/1/1
 */
class SealedClass {
    public static void main(String[] args) {
        Shape shape = new Shape.ColoredCircle("ColoredCircle");
        System.out.println(shape.id);
    }
}

/*封闭类的声明使用 sealed 类修饰符
然后在所有的 extends 和 implements
 语句之后,使用 permits 指定允许扩展该封闭类的子类*/

// 由 permits 关键字指定的许可子类(permitted subclasses),必须和封闭类处于同一模块(module)
// 或者包空间(package)里;如果封闭类和许可类是在同一个模块里,那么它们可以处于不同的包空间里

// 如果允许扩展的子类和封闭类在同一个源代码文件里,封闭类可以不使用 permits 语句,Java 编译器
// 将检索源文件,在编译期为封闭类添加上许可的子类
abstract sealed class Shape permits Shape.Circle, Shape.ColoredSquare, Shape.Rectangle, Shape.Square {
    public final String id;

    public Shape(String id) {
        this.id = id;
    }

    public abstract double area();

    // 解封类
    public static non-sealed class Circle extends Shape {

        public Circle(String id) {
            super(id);
        }

        @Override
        public double area() {
            return 0;
        }
    }

    // 封闭类
    public static final class Square extends Shape {

        public Square(String id) {
            super(id);
        }

        @Override
        public double area() {
            return 0;
        }
    }
    // 封闭类
    @Getter
    @Setter
    @ToString
    public static final class Rectangle extends Shape {

        private int length;
        private int width;
        public Rectangle(String id) {
            super(id);
        }

        @Override
        public double area() {
            return length * width;
        }
    }
    // 终极类
    public static final class ColoredSquare extends Shape {

        public ColoredSquare(String id) {
            super(id);
        }

        @Override
        public double area() {
            return 0;
        }
    }
    // 既不是封闭类,也不是许可类 普通类
    public static class ColoredCircle extends Circle {

        public ColoredCircle(String id) {
            super(id);
        }

        @Override
        public double area() {
            return 0;
        }
    }
}
/*许可类的声明需要满足下面的三个条件*/

// 1 : 许可类必须和封闭类处于同一模块(module)或者包空间(package)里,也就是说,在编译的时候,封闭类必须可以访问它的许可类
// 2 : 许可类必须是封闭类的直接扩展类
// 3 : 可类必须声明是否继续保持封闭
// 许可类可以声明为终极类(final),从而关闭扩展性；
// 许可类可以声明为封闭类(sealed),从而延续受限制的扩展性；
// 许可类可以声明为解封类(non-sealed),从而支持不受限制的扩展性

// tips : 由于许可类必须是封闭类的直接扩展,因此许可类不具备传递性
