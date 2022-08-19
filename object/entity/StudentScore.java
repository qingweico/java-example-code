package object.entity;

import java.util.Objects;

/**
 * @author zqw
 * @date 2020/1/28
 */
public class StudentScore {
    private String name;
    private int chinese;
    private int math;
    private int english;

    public StudentScore() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getSum() {
        return math + chinese + english;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentScore studentScore = (StudentScore) o;
        return chinese == studentScore.chinese &&
                math == studentScore.math &&
                english == studentScore.english &&
                Objects.equals(name, studentScore.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, chinese, math, english);
    }
}
