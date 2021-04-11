package effective;

import java.util.*;

/**
 * 只针对异常的情况才使用异常
 *
 * @author:qiming
 * @date: 2021/2/6
 */
public class Article69 {
    private static final Student[] students = new Student[10];
    public static void main(String[] args) {

        // Bad
        try {
            int i = 0;
            while (true) {
                students[i++].move();
            }
        }catch (ArrayIndexOutOfBoundsException ex) {

        }

        // Normal
        for(Student student : students) {
            student.move();
        }

        // The exception-based schema is actually much slower than the standard schema.


        // Exceptions should only be used in the case of exceptions; they should never
        // be used in the normal control flow.

        List<Student> collection = new ArrayList<>(Arrays.asList(students));
        for(Iterator<Student> iterator = collection.iterator(); iterator.hasNext();) {
            Student s = iterator.next();
        }

        try {
            Iterator<Student> studentIterator = collection.iterator();
            while (true) {
                studentIterator.next();
            }
        }catch (NoSuchElementException ex) {

        }
    }


}
