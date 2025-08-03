package thinking.enumerated;

import org.apache.commons.lang3.StringUtils;
import cn.qingweico.io.Print;

import static cn.qingweico.io.Print.print;

/**
 * @author zqw
 * @date 2021/4/3
 */
public class EnumClass {
    public static void main(String[] args) {
        for (Shrubbery s : Shrubbery.values()) {
            // Returns the order in which each enum instance is declared(from 0 begin)
            Print.println(s + " ordinal: " + s.ordinal());
            print(s.compareTo(Shrubbery.CRAWLING) + " ");
            print(s.equals(Shrubbery.CRAWLING) + " ");
            Print.println(s == Shrubbery.CRAWLING);
            Print.println(s.getDeclaringClass());
            Print.println(s.name());
            Print.println("----------------------");

        }
        String values = "GROUND CRAWLING HANGING";

        for (String s : values.split( StringUtils.SPACE)) {
            Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
            Print.println(shrub);
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
        Print.println("[Constructor]: " + this);
    }
}

