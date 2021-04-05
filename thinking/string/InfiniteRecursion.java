package thinking.string;

/**
 * @author:qiming
 * @date 2021/1/17
 */
public class InfiniteRecursion {

    // Stack overflow: Infinite calls to the toString method.
    // Using super.toString() instead of this.
    public String toString() {
        return "InfiniteRecursion address " + this + "\n";
    }

    public static void main(String[] args) {
        InfiniteRecursion v = new InfiniteRecursion();
        System.out.println(v);
    }
}
