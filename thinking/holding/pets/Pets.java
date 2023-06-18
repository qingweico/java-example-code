package thinking.holding.pets;
import java.util.*;

/**
 * @author zqw
 */
public class Pets {
  public static final AbstractPetCreator creator =
    new LiteralPetCreator();
  public static Pet randomPet() {
    return creator.randomPet();
  }
  public static Pet[] createArray(int size) {
    return creator.createArray(size);
  }
  public static ArrayList<Pet> arrayList(int size) {
    return creator.arrayList(size);
  }
}
