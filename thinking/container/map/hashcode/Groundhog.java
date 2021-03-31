package thinking.container.map.hashcode;

/**
 * @author:qiming
 * @date: 2021/2/24
 */
// Looks plausible, but doesn't work as a HashMap key
public class Groundhog {
    protected int number;
    public Groundhog(int n) {
        number = n;
    }
    public String toString() {
        return "Groundhog #" + number;
    }
}
