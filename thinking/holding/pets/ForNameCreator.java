package thinking.holding.pets;

import util.Print;

import java.util.*;

/**
 * @author zqw
 * @date 2021/3/23
 */
public class ForNameCreator extends AbstractPetCreator {
    private static final List<Class<? extends Pet>> TYPES =
            new ArrayList<>();
    // Types that you want to be randomly created:

    private static final String[] TYPE_NAMES = {
            "thinking.holding.pets.Mutt",
            "thinking.holding.pets.Pug",
            "thinking.holding.pets.EgyptianMau",
            "thinking.holding.pets.Manx",
            "thinking.holding.pets.Cymric",
            "thinking.holding.pets.Rat",
            "thinking.holding.pets.Mouse",
            "thinking.holding.pets.Hamster"
    };

    @SuppressWarnings("unchecked")
    private static void loader() {
        try {
            for (String name : TYPE_NAMES) {
                TYPES.add(
                        (Class<? extends Pet>) Class.forName(name));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        loader();
    }

    @Override
    public List<Class<? extends Pet>> types() {
        return TYPES;
    }

    public static void main(String[] args) {
        ForNameCreator creator = new ForNameCreator();
        Print.toPrint(creator.types());

    }
}
