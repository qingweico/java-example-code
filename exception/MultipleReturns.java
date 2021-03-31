package exception;

import static util.Print.print;

/**
 * Use finally in return
 *
 * @author:qiming
 * @date: 2021/1/17
 */
public class MultipleReturns {
    public static void f(int i){
        print("Initialization that requires cleanup");
        try {
            print("Point 1");
            if(i == 1) return;
            print("Point 2");
            if(i == 2) return;
            print("Point 3");
            if(i == 3) return;
            print("End");
            return;
            // It doesn't matter where you return from.
        }finally {
            print("Performing cleanup");
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 4; i++) {
            f(i);
        }
    }
}
