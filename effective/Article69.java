package effective;

import com.github.javafaker.Faker;
import object.Student;

import java.util.*;

/**
 * 只针对异常的情况才使用异常
 *
 * @author:qiming
 * @date: 2021/2/6
 * @see exception.ExceptionPerformance
 */
public class Article69 {

    public static void main(String[] args) {
        Student[] students = new Student[10];
        Faker faker = new Faker(Locale.CHINA);
        for (int i = 0; i < students.length; i++) {
            Student student = new Student();
            Integer score = (int) faker.number().randomDouble(0, 0, 750);
            String name = faker.name().fullName();
            student.setName(name).setScore(score);
            students[i] = student;
        }

        // Bad
        try {
            int i = 0;
            while (true) {
                System.out.println(students[i++].get());
            }
        } catch (ArrayIndexOutOfBoundsException ex) {

        }

        // Normal
        for (Student student : students) {
            System.out.println(student.get());
        }

        // The exception-based schema is actually much slower than the standard schema.


        // Exceptions should only be used in the case of exceptions; they should never
        // be used in the normal control flow.

        // standard mode
        List<Student> collection = new ArrayList<>(Arrays.asList(students));
        for (Iterator<Student> iterator = collection.iterator(); iterator.hasNext(); ) {
            Student s = iterator.next();
            System.out.println(s.get());
        }

        // based exception pattern
        // Do not use this hideous code for iteration for a collection!
        try {
            Iterator<Student> studentIterator = collection.iterator();
            while (true) {
                Student student = studentIterator.next();
                System.out.println(student.get());
            }
        } catch (NoSuchElementException ex) {

        }
    }
}
