package thinking.holding;

import cn.qingweico.io.Print;
import thinking.holding.pets.Cat;
import thinking.holding.pets.Pet;

import java.util.*;

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
        Print.println(petMap);
        Pet dog = petMap.get("My Dog");
        Print.println(dog);
        Print.println(petMap.containsKey("My Dog"));
        Print.println(petMap.containsValue(dog));
    }
}
