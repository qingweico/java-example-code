package thinking.holding;

import static util.Print.println;
import static util.Print.print;

/**
 * @author zqw
 * @date 2021/1/18
 */
public class StackCollision {
    public static void main(String[] args) {
        thinking.holding.Stack<String> stack = new Stack<>();
        for(String s : "My dog has fleas".split(" ")){
            stack.push(s);
        }
        while (!stack.isEmpty()){
            print(stack.pop() + " ");
        }
        println();
        java.util.Stack<String> stringStack = new java.util.Stack<>();
        for(String s : "My dog has fleas".split(" ")){
            stringStack.push(s);
        }
        while (!stringStack.isEmpty()){
            print(stringStack.pop() + " ");
        }

    }
}
