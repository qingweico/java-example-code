package effective;

import java.util.Objects;

/**
 * 覆盖equals时总要覆盖hashcode
 *
 * @author 周庆伟
 * @date: 2020/10/25
 */
class Student {
    int age;
    String name;

    Student(int age, String name) {
        this.age = age;
        this.name = name;
    }
    public void move() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
}

public class Article11 {

    public static void main(String[] args) {
        Student student = new Student(22, "we");
        Student student1 = new Student(22, "we");
        System.out.println(student);
        System.out.println(student1);
        System.out.println(student.equals(student1)); //true
    }
}
