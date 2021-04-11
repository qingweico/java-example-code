package thinking.genericity;

import thinking.holding.pets.Person;
import thinking.holding.pets.Pet;
import util.New;

import java.util.List;
import java.util.Map;

/**
 * @author:qiming
 * @date: 2021/4/9
 */
public class SimplerPets {
    public static void main(String[] args) {
        Map<Person, List<? extends Pet>> petPeople = New.map();
    }
}
