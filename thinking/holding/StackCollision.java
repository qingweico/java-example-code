package thinking.holding;

import static util.Print.print;
import static util.Print.printnb;

/**
 * @author:周庆伟
 * @date: 2021/1/18
 */
public class StackCollision {
    public static void main(String[] args) {
        thinking.holding.Stack<String> stack = new Stack<>();
        for(String s : "My dog has fleas".split(" ")){
            stack.push(s);
        }
        while (!stack.isEmpty()){
            printnb(stack.pop() + " ");
        }
        print();
        java.util.Stack<String> stringStack = new java.util.Stack<>();
        for(String s : "My dog has fleas".split(" ")){
            stringStack.push(s);
        }
        while (!stringStack.isEmpty()){
            printnb(stringStack.pop() + " ");
        }

    }
}
