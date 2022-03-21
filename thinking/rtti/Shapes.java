package thinking.rtti;

import java.util.Arrays;
import java.util.List;

/**
 * RTTI Run-Time Type Identification: At runtime, identify the type of object
 *
 * @author zqw
 * @date 2021/1/8
 */
class Shapes {
    public static void main(String[] args) {

        // Shape objects are transformed upward when they are put into an array of
        // List<Shape> types, but when they are transformed upward to a Shape object,
        // the specific type of the Shape object is lost. Arrays are simply objects
        // of Shape.
        List<Shape> shapeList = Arrays.asList(
                new Circle(),
                new Square(),
                new Triangle());

        // When an element is pulled from an array, the container which actually holds everything
        // as an Object and automatically transforms the result into a Shape, this is the most
        // basic form of use for RTTI.
        // Then there are the polymorphic mechanisms, for non-static methods in a subclass,
        // the parent class is dynamically bound(That is, the method of which subclass is
        // called is decided at run time) at run time.
        // tips: All type conversions in java are checked correctly at run time.
        for (Shape shape : shapeList) {
            shape.draw();
        }
    }
}
abstract class Shape{
    void draw(){
        System.out.println(this + ".draw()");
    }

    /**
     * toString
     * @return String
     */
    @Override
    abstract public String toString();
}
class Circle extends Shape{

    @Override
    public String toString() {
        return "Circle";
    }
}
class Square extends Shape{

    @Override
    public String toString() {
        return "Square";
    }
}
class Triangle extends Shape{

    @Override
    public String toString() {
        return "Triangle";
    }
}
