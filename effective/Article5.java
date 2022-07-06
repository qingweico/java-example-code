package effective;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * 优先考虑依赖注入来引入功能
 *
 * @author zqw
 * @date 2021/3/23
 */
class Article5 {
    public static void main(String[] args) {

    }
}

/**
 * Inappropriate use of static utility - inflexible & untestable
 */
class SpellChecker {
    private static Lexicon dictionary;
    private static ArrayList<String> suggestionLists;
    static  {
        dictionary = new Lexicon();
        suggestionLists = new ArrayList<>();
    }
    /**
     * Non-instantiable
     */
    private SpellChecker() {
    }

    public static boolean isValid(String word) {
        return word.trim().matches("([a-zA-Z]+)");
    }

    public static List<String> suggestions(String typo) {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        String word  = "speak";
    }

}

/**
 * Inappropriate use of singleton - inflexible & untestable
 */
class SpellCheckerUsingSingleton {
    private final Lexicon dictionary = null;

    /**
     * Non-instantiable
     */
    private SpellCheckerUsingSingleton() {
    }

    public static SpellCheckerUsingSingleton checker = new SpellCheckerUsingSingleton();

    public static boolean isValid(String word) {
        return word.trim().matches("([a-zA-Z]+)");
    }

    public static List<String> suggestions(String typo) {
        return new ArrayList<>();
    }

}

class Lexicon {
    private static List<String> data = new ArrayList<>();
}

// Static utility classes and Singleton classes are not appropriate
// for classes that need to reference the underlying resources.


/**
 * Dependency injection providers flexibility and testability
 */
class SpellCheckerUsingDi {
    private final Lexicon dictionary;

    public SpellCheckerUsingDi(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public static boolean isValid(String word) {
        return word.trim().matches("([a-zA-Z]+)");
    }

    public static List<String> suggestions(String typo) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "SpellCheckerUsingDI{" + "dictionary=" + dictionary + '}';
    }
}

/**
 * Pass the resource factory to the constructor, this type of factory is embodied in the
 * factory method, Supplier<T> is a good way.
 */
class StringFactory {
    /**
     * @param supplier The string of Producer
     * @return String
     */
    public static String create(Supplier<? extends String> supplier) {
        return supplier.get();
    }

    public static String randomString() {
        StringBuilder stringBuilder = new StringBuilder(10);
        for (int i = 0; i < stringBuilder.capacity(); i++) {
            // A ~ Z 65 - 90 a ~ z 97 - 122
            int rand = ThreadLocalRandom.current().nextInt(65, 123);
            stringBuilder.append((char) rand);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(create(StringFactory::randomString));
    }
}
// Dependency injection framework : [Dagger] [Guice] [Spring]
