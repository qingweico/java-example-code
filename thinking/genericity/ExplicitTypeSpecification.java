package thinking.genericity;

import thinking.holding.pets.Person;
import thinking.holding.pets.Pet;
import cn.qingweico.collection.CollUtils;
import cn.qingweico.io.Print;

import java.util.List;
import java.util.Map;

/**
 * Display type description but rarely used!
 *
 * @author zqw
 * @date 2021/4/9
 */
class ExplicitTypeSpecification {
    static void f(Map<Person, List<Pet>> petPerson) {
        Print.printMap(petPerson);
    }


    // After JDK7, the compiler can infer the display type parameters.

    public static void main(String[] args) {
        f(CollUtils.map());
        // f(New.<Person, List<Pet>>map());
    }
}
