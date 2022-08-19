package thinking.holding.pets;

import java.util.*;

/**
 * @author zqw
 */
abstract class AbstractPetCreator {
    private final Random rand = new Random(47);

    // The List of the different types of Pet to create:

    /**
     * All kinds of pet class types
     *
     * @return The list of the different types of pet classes.
     */
    public abstract List<Class<? extends Pet>> types();

    public Pet randomPet() {
        // Create one random Pet
        int n = rand.nextInt(types().size());
        try {
            return types().get(n).getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Pet[] createArray(int size) {
        Pet[] result = new Pet[size];
        for (int i = 0; i < size; i++) {
            result[i] = randomPet();
        }
        return result;
    }

    public ArrayList<Pet> arrayList(int size) {
        ArrayList<Pet> result = new ArrayList<>();
        Collections.addAll(result, createArray(size));
        return result;
    }
}
