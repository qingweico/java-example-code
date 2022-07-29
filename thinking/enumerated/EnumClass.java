package thinking.enumerated;

import org.apache.commons.lang3.StringUtils;

import static util.Print.print;
import static util.Print.printnb;

/**
 * @author zqw
 * @date 2021/4/3
 */
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
        String values = "GROUND CRAWLING HANGING";

        for (String s : values.split( StringUtils.SPACE)) {
            Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
            print(shrub);
        }
    }

}

enum Shrubbery {
    /**
     *
     */
    GROUND,
    /**
     *
     */
    CRAWLING,
    /**
     *
     */
    HANGING;

    Shrubbery() {
        print("[Constructor]: " + this);
    }
}

