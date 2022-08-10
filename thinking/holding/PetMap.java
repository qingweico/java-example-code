package thinking.holding;

import thinking.holding.pets.Cat;
import thinking.holding.pets.Pet;

import java.util.*;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/3/22
 */
class PetMap {
    public static void main(String[] args) {
        Map<String, Pet> petMap = new HashMap<>(3);
        petMap.put("My Cat", new Cat("Molly"));
        petMap.put("My Dog", new Cat("Ginger"));
        petMap.put("My Hamster", new Cat("Bosco"));
        print(petMap);
        Pet dog = petMap.get("My Dog");
        print(dog);
        print(petMap.containsKey("My Dog"));
        print(petMap.containsValue(dog));
    }
}
