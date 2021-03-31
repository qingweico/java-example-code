package thinking.container.map.hashcode;

import java.util.Objects;

/**
 * @author:周庆伟
 * @date: 2021/2/24
 */
// A class that's used as a key in a HashMap must override hashcode() and equals().
public class Groundhog2 extends Groundhog{
    public Groundhog2(int n) {
        super(n);
    }
    @Override
    public int hashCode() {
        return number;
    }
    @Override
    public boolean equals(Object o) {
       return o instanceof Groundhog2
               && (number == ((Groundhog2) o).number);
    }
}
