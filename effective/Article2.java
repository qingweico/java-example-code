package effective;

import com.google.common.base.MoreObjects;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import static effective.NyPizza.Size.SMALL;
import static effective.Pizza.Topping.*;

/**
 * 遇到多个构造器参数时要考虑使用构建器
 *
 * @author zqw
 * @date 2021/2/22
 */
class Article2 {
    public static void main(String[] args) {

    }
}

/**
 * Telescoping constructor pattern - does not scale well!
 */
class NutritionFacts {
    /**
     * (ml)             required
     */
    private final int servingSize;
    /**
     * (per container)  required
     */
    private final int servings;
    /**
     * (per serving)    optional
     */
    private final int calories;
    /**
     * (g/serving)      optional
     */
    private final int fat;
    /**
     * (mg/serving)     optional
     */
    private final int sodium;
    /**
     * (g/serving)      optional
     */
    private final int carbohydrate;

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public NutritionFacts(int servingSize, int servings,
                          int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings,
                          int calories, int fat, int sodium,
                          int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("servingSize", servingSize)
                .add("servings", servings)
                .add("calories", calories)
                .add("fat", fat)
                .add("sodium", sodium)
                .add("carbohydrate", carbohydrate)
                .toString();
    }

    public static void main(String[] args) {
        NutritionFacts nutritionFacts = new NutritionFacts(100, 20);
        System.out.println(nutritionFacts);
    }
}
// In short, the overlapping constructor pattern works, but the client code is
// harder to write and harder to read when you have a lot of parameters.


/**
 * JavaBeans Pattern - allows inconsistency, mandates mutability
 */
class NutritionFactsOfUsingJavaBeans {
    /**
     * Required; not default value
     */
    private int servingSize = -1;
    /**
     * Required; not default value
     */
    private int servings = -1;
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public NutritionFactsOfUsingJavaBeans() {
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("servingSize", servingSize)
                .add("servings", servings)
                .add("calories", calories)
                .add("fat", fat)
                .add("sodium", sodium)
                .add("carbohydrate", carbohydrate)
                .toString();
    }

    public static void main(String[] args) {

        // Instances are easy to create and the code is easy to read,
        // but the serious disadvantage is that JavaBeans can be in
        // inconsistent states during construction.
        NutritionFactsOfUsingJavaBeans cocaCola = new NutritionFactsOfUsingJavaBeans();
        cocaCola.setServingSize(240);
        cocaCola.setServings(8);
        cocaCola.setCalories(100);
        cocaCola.setSodium(35);
        cocaCola.setCalories(27);
        cocaCola.setCarbohydrate(200);
        System.out.println(cocaCola);
    }
}

class NutritionFactsOfUsingBuilder {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
        // Required parameters
        private final int servingSize;
        private final int servings;

        // Optionals parameters - initialized to default values
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFactsOfUsingBuilder builder() {
            return new NutritionFactsOfUsingBuilder(this);
        }
    }

    private NutritionFactsOfUsingBuilder(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    @Override
    public String toString() {
        return "NutritionFactsOfUsingBuilder{" +
                "servingSize=" + servingSize +
                ", servings=" + servings +
                ", calories=" + calories +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", carbohydrate=" + carbohydrate +
                '}';
    }

    public static void main(String[] args) {
        NutritionFactsOfUsingBuilder cocaCoal = new NutritionFactsOfUsingBuilder
                .Builder(240, 8)
                .calories(100)
                .sodium(35)
                .carbohydrate(27)
                .builder();
        System.out.println(cocaCoal);
    }
}

/**
 * Builder pattern for class hierarchies
 */
abstract class Pizza {
    public enum Topping {
        /**
         * ~
         */
        HAM,
        MUSHROOM,
        ONION,
        PEPPER,
        SAUSAGE
    }

    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        /**
         * The builder of Pizza
         *
         * @return {@code Pizza}
         */
        public abstract Pizza build();

        /**
         * Subclasses must override this method to return "this".
         *
         * @return {@code T}
         */
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }
}

class NyPizza extends Pizza {
    public enum Size {
        /**
         * ~
         */
        SMALL,
        MEDIUM,
        LARGE
    }

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("toppings", toppings)
                .add("size", size)
                .toString();
    }
}

class Calzone extends Pizza {
    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {
        private boolean sauceInside = false;

        public Builder sauceInside() {
            sauceInside = true;
            return this;
        }

        @Override
        public Calzone build() {
            // Covariant return type
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("toppings", toppings)
                .add("sauceInside", sauceInside)
                .toString();
    }

    public static void main(String[] args) {
        NyPizza pizza = new NyPizza.Builder(SMALL)
                .addTopping(SAUSAGE).addTopping(ONION).build();
        Calzone calzone = new Calzone.Builder()
                .addTopping(HAM).sauceInside().build();
        System.out.println(pizza);
        System.out.println(calzone);

    }
}
// To sum up, the Builder pattern is a good choice when designing classes with multiple
// parameters in their constructor or static factory, especially if most of the
// parameters are optional or of the same type.
// The client-side code using Builder mode is easier to read and write than using the
// overlapping constructor mode, and the Builder is more secure than JavaBeans.