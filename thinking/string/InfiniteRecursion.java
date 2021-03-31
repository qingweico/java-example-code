package thinking.string;

public class InfiniteRecursion {
    /*
     * Stack overflow: Infinite calls to the toString method
     */
    public String toString() {
        return "InfiniteRecursion address " + this + "\n";
    }

//    public String toString(){
//        return "InfiniteRecursion address " + super.toString() + "\n";
//    }
    public static void main(String[] args) {
        InfiniteRecursion v = new InfiniteRecursion();
        System.out.println(v);
    }
}
