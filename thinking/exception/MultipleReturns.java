package thinking.exception;

import util.Print;

/**
 * Use finally in return
 *
 * @author zqw
 * @date 2021/1/17
 */
public class MultipleReturns {
    public static void f(int i){
        Print.println("Initialization that requires cleanup");
        try {
            Print.println("Point 1");
            if(i == 1) {
                return;
            }
            Print.println("Point 2");
            if(i == 2) {
                return;
            }
            Print.println("Point 3");
            if(i == 3) {
                return;
            }
            Print.println("End");
            return;
            // It doesn't matter where you return from.
        }finally {
            Print.println("Performing cleanup");
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 4; i++) {
            f(i);
        }
    }
}
