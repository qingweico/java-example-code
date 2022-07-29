package effective;

import annotation.Pass;
import object.Student;

/**
 * 覆盖equals时总要覆盖hashcode
 *
 * @author zwq
 * @date 2020/10/25
 * @see object.Student
 */

@Pass
class Article11 {

    public static void main(String[] args) {
        Student s1 = new Student(22, "li");
        Student s2 = new Student(22, "li");
        System.out.println(s1);
        System.out.println(s2);
        // true
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode());
        System.out.println(s1.hashCode());
    }
}
