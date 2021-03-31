package effective;

import java.util.*;

/**
 * 基本类型优于装箱基本类型
 *
 * @author:周庆伟
 * @date: 2020/11/23
 */

/*基本类型和装箱基本类型的三个区别
   1): 基本类型只有值，而装箱基本类型则具有与它们的值不同的同一性
   2): 基本类型只有函数值，而装箱基本类型则都有一个非函数值，除了它对应的基本类型的所有函数值以外，还有个null
   3): 基本类型通常比装箱基本类型更节省时间和空间
 */

public class Article61 {

    //broken
    //static Comparator<Integer> naturalOrder = (i ,j) -> (i < j) ? -1 : (i == j ? 0 : 1);

    //solved
    static Comparator<Integer> naturalOrder = (iBoxed, jBoxed) -> {
        int i = iBoxed, j = jBoxed; //Auto-unboxing
        return i < j ? -1 : (i == j ? 0 : 1);
    };

    public static void main(String[] args) {

        // There will be got back -1 ,not 0 why?
        // The expression i < j performs a calculation that results in the Integer instances of
        // references to i and j being automatically boxed, that is, it extracts their base types
        // executes the expression i == j which performs an identity comparison on the references
        // of the two objects,if i and j refer to different instances of Integers representing the
        // same int value, this comparison returns false, and the comparator mistakenly returns -1,
        // indicating that the first Integer is greater than the second,
        // so it is almost always wrong to use the == operator on boxing base types.
        System.out.println(naturalOrder.compare(new Integer(42), new Integer(42)));


    }


}
class Unbelievable{

    //solved: use int to instead of Integer.
    static Integer i;

    public static void main(String[] args) {

        // There will be thrown java.lang.NullPointerException why?
        // Integer will be initialized to NULL, and the boxing primitive is automatically
        // unboxed when a mixture of primitive types and boxing primitive types is used in an operation,
        // so null object references are automatically boxed, throwing a null-pointer exception.
        if(i == 42){
            System.out.println("Unbelievable!");
        }
    }

}
class UseBoxingAsLocalVariable{
    public static void main(String[] args) {

        // This can lead to a decrease in program performance.
        Long sum = 0L;
        for(long i = 0;i < Integer.MAX_VALUE;i++){
            sum += i;
        }
        System.out.println(sum);
    }
    // when do you use basic boxing types?
    //1> As elements in a collection, keys and values.
    //2> In parameterized types and methods, you must use boxing primitives as type parameters.
          //eg: you cannot declare variables of type ThreadLocal<int>.
    //3> You must use boxed primitive types when making calls to reflection methods.


    //To sum up: 1: Basic boxing is better than basic boxing types, which are simpler and faster.
    //           2: Autoboxing reduces the complexity of using the basic type of boxing, but it does not reduce its complexity.
    //           3: A null pointer exception is thrown when the program is unboxing.
    //           4: When a program boxes a base-type value, it results in high resource consumption and unnecessary object creation.

}


