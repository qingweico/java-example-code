package effective;

import java.util.Objects;

/**
 * 覆盖equals时总要覆盖hashcode
 *
 * @author:qiming
 * @date: 2020/10/25
 */
class Student {
    String name;
    Integer score;

    Student(Integer age, String name) {
        this.score = age;
        this.name = name;
    }

    public Student(){}

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Student setScore(Integer score) {
        this.score = score;
        return this;
    }

    public String get() {
        return this.name + ": " + this.score + "\n";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(score, student.score) &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, name);
    }
}

public class Article11 {

    public static void main(String[] args) {
        Student student = new Student(22, "li");
        Student student1 = new Student(22, "li");
        System.out.println(student);
        System.out.println(student1);
        System.out.println(student.equals(student1)); //true
        System.out.println(student.hashCode());
        System.out.println(student1.hashCode());
    }
}
