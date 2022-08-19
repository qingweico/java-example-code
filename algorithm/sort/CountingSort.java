package algorithm.sort;

import com.github.javafaker.Faker;
import object.entity.Student;
import org.junit.Test;

import java.util.Locale;

/**
 * 计数排序 O(n + r)
 * n: 数据规模
 * r: 数据范围
 * 稳定的排序
 * @author:qiming
 * @date: 2021/11/3
 */
public class CountingSort {

    private static final int COUNT = 10;
    private static final int BOUND = 750;


    @Test
    public void sort() {
        Student[] students = new Student[COUNT];
        Faker faker = new Faker(Locale.ENGLISH);

        for (int i = 0; i < students.length; i++) {
            Student s = new Student();
            String name = faker.name().fullName();
            Integer score = faker.number().numberBetween(0, BOUND);
            s.setName(name);
            s.setScore(score);
            students[i] = s;

        }
        int[] cnt = new int[BOUND];
        int[] index = new int[BOUND + 1];
        Student[] help = new Student[COUNT];
        for (Student s : students) {
            cnt[s.getScore()]++;
        }
        for (int i = 0; i < BOUND; i++) {
            index[i + 1] = index[i] + cnt[i];
        }
        for (Student s : students) {
            help[index[s.getScore()]] = s;
            index[s.getScore()]++;
        }
        System.arraycopy(help, 0, students, 0, COUNT);
        for (Student s : students) {
            System.out.println(s.get());
        }
    }

}
