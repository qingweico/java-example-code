

package thinking.holding.pets;

import java.util.*;

// 使用类字面量


class LiteralPetCreator extends AbstractPetCreator {
    // No try block needed.

    public static final List<Class<? extends Pet>> ALL_TYPES =
            List.of(Pet.class, Dog.class, Cat.class, Rodent.class, Mutt.class, Pug.class, EgyptianMau.class, Manx.class, Cymric.class, Rat.class, Mouse.class, Hamster.class);
    // Types for random creation:

    private static final List<Class<? extends Pet>> TYPES =
            ALL_TYPES.subList(ALL_TYPES.indexOf(Mutt.class),
                    ALL_TYPES.size());

    @Override
    public List<Class<? extends Pet>> types() {
        return TYPES;
    }

    public static void main(String[] args) {
        System.out.println(TYPES);
    }
}