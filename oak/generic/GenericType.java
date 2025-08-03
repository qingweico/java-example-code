package oak.generic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import object.entity.Student;
import object.entity.User;
import cn.qingweico.io.Print;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 获取类的泛型信息
 *
 * @author zqw
 * @date 2023/6/17
 */
@Slf4j
@SuppressWarnings("unused")
class GenericType<T, U, R> {
    List<Type> getGenericEntityType() {
        List<Type> result = new ArrayList<>();
        Type type = getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType pt) {
            Type[] types = pt.getActualTypeArguments();
            if(types.length == 1) {
                result.add(types[0]);
                printGenericType(type);
            }
            else {
                result.addAll(Arrays.asList(types));
                Arrays.stream(types).forEach(this::printGenericType);
            }
        }else {
            throw new RuntimeException("Generic type Not Found");
        }
        return result;
    }
    void printGenericType(Type type) {
        if(type instanceof ParameterizedType pt) {
            Type[] types = pt.getActualTypeArguments();
            Arrays.stream(types).forEach(this::printGenericType);
        }else {
            log.info("{}", type);
        }
    }
}
class BasicType extends GenericType<Integer, Boolean, Long> {

    public static void main(String[] args) {
        BasicType st = new BasicType();
        Print.printColl(st.getGenericEntityType());
    }
}
class ComplexType extends GenericType<List<User>, Map<String, Map<String, User>>, Set<List<Boolean>>> {
    public static void main(String[] args) {
        ComplexType ct = new ComplexType();
        Print.printColl(ct.getGenericEntityType());
    }
}

class JsonWithinGenericType {
    public static void main(String[] args) throws JsonProcessingException {
        String json = """
                [
                    {"name": "Zhou","score": "100"},
                    {"name": "Li","score": "99"}
                ]
                """;
        TypeReference<List<Student>> tr = new TypeReference<>() {
        };
        List<Student> list = new ObjectMapper().readValue(json, tr);
        Print.printColl(list);
    }
}
