package object.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author zqw
 * @date 2020/1/28
 */
@Setter
@Getter
public class StudentScore {
    private String name;
    private int chinese;
    private int math;
    private int english;

    public StudentScore() {
        super();
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
