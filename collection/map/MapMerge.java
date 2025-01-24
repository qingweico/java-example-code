package collection.map;

import object.entity.Student;
import util.ObjectFactory;
import util.Print;
import util.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author zqw
 * @date 2023/2/10
 * @see HashMap#merge(Object, Object, BiFunction)
 */
class MapMerge {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < Constants.TEN; i++) {
            Student student = ObjectFactory.create(Student.class, true);
            students.add(student);
        }
        HashMap<String, Integer> map = new HashMap<>(16);
        students.forEach(student ->
                // key 相同 value 相加
                map.merge(student.getName(), student.getScore(), Integer::sum));
        Print.printMap(map);
    }
}
