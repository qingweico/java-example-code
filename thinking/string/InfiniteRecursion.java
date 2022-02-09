package thinking.string;

/**
 * @author zqw
 * @date 2021/1/17
 */
class InfiniteRecursion {

    /**
     * Stack overflow: Infinite calls to the toString method.
     * Using super.toString() instead of this.
     */
    @Override
    public String toString() {
        return "InfiniteRecursion address " + this + "\n";
    }

    public static void main(String[] args) {
        InfiniteRecursion v = new InfiniteRecursion();
        System.out.println(v);
    }
}
