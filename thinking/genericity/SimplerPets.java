package thinking.genericity;

import thinking.holding.pets.Person;
import thinking.holding.pets.Pet;
import util.collection.CollUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zqw
 * @date 2021/4/9
 */
class SimplerPets {
    public static void main(String[] args) {
        Map<Person, List<? extends Pet>> petPeople = CollUtils.map();
        System.out.println(petPeople);
    }
}
