package thinking.container.map.hashcode;

/**
 * Looks plausible, but doesn't work as a HashMap key
 *
 * @author zqw
 * @date 2021/2/24
 */
class Groundhog {
    protected int number;

    public Groundhog(int n) {
        number = n;
    }

    @Override
    public String toString() {
        return "Groundhog #" + number;
    }
}
