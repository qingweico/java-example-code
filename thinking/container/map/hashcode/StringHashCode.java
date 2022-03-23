package thinking.container.map.hashcode;

/**
 * @author zqw
 * @date 2021/4/3
 */
class StringHashCode {
    public static void main(String[] args) {
        // If you have multiple strings in your program that all contain the same
        // character instance, then they all map to the same memory area.
        String[] hellos = "Hello Hello".split(" ");
        System.out.println(hellos[0].hashCode());
        System.out.println(hellos[1].hashCode());
    }
}
