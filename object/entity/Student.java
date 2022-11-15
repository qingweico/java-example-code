package object.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import util.constants.Symbol;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author zqw
 * @date 2021/11/17
 */
public class Student implements Serializable {
    String name;
    Integer score;

    public Student(Integer age, String name) {
        this.score = age;
        this.name = name;
    }

    public Student() {
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Student setScore(Integer score) {
        this.score = score;
        return this;
    }

    public String get() {
        return this.name + Symbol.COLON + this.score;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
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
        return Objects.equals(score, student.score) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}