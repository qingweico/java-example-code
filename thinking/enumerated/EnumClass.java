package thinking.enumerated;

import static util.Print.print;
import static util.Print.printnb;

/**
 * @author:qiming
 * @date: 2021/4/3
 */
enum Shrubbery {
    GROUND, CRAWLING, HANGING;

    Shrubbery() {
        print();
    }
}

public class EnumClass {
    public static void main(String[] args) {
        for (Shrubbery s : Shrubbery.values()) {
            // Returns the order in which each enum instance is declared(from 0 begin)
            print(s + " ordinal: " + s.ordinal());
            printnb(s.compareTo(Shrubbery.CRAWLING) + " ");
            printnb(s.equals(Shrubbery.CRAWLING) + " ");
            print(s == Shrubbery.CRAWLING);
            print(s.getDeclaringClass());
            print(s.name());
            print("----------------------");

        }

        for (String s : "GROUND CRAWLING HANGING".split(" ")) {
            Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
            print(shrub);
        }
    }

}
