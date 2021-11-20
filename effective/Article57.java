package effective;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 将局部变量的作用域最小化
 *
 * @author:qiming
 * @date: 2020/10/25
 */
// The most powerful way to minimize the scope of a local variable
// is to declare it where it is used the first time.
public class Article57 {
    public static void main(String[] args) {
        // A for loop is better than a while loop if the contents of the
        // loop variable are no longer needed after the loop terminates.
        List<Integer> c = new ArrayList<>();
        c.add(1);
        c.add(2);
        List<Integer> c2 = new ArrayList<>();
        c2.add(1);
        c2.add(2);
        // Preferred idiom for iterating over a collection or array.
        for(Integer i : c) {
            System.out.println(i);
        }
        Iterator<Integer> i = c.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
        Iterator<Integer> i2 = c2.iterator();
        while (i.hasNext()) {                    //BUG!
            System.out.println(i2.next());
        }
        // it`s good way to use it
        for (Iterator<Integer> i1 = c.iterator(); i.hasNext(); ) {
            System.out.println(i1.next());
        }
    }
}
