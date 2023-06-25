package thinking.holding.pets;
import java.util.*;

/**
 * @author zqw
 */
public class Pets {
  public static final AbstractPetCreator CREATOR =
    new LiteralPetCreator();
  public static Pet randomPet() {
    return CREATOR.randomPet();
  }
  public static Pet[] createArray(int size) {
    return CREATOR.createArray(size);
  }
  public static ArrayList<Pet> arrayList(int size) {
    return CREATOR.arrayList(size);
  }
}
