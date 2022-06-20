package thinking.exception;

import static util.Print.print;

/**
 * Catch exception hierarchies
 *
 * @author:qiming
 * @date: 2021/1/19
 */
public class ExceptionMatching {

    public static void main(String[] args) {
        // Catch the exact exception
        try {
           throw new Sneeze();
        }catch (Sneeze e){
            print("Caught sneeze");
            // catch (Annoyance e) catches Annoyance and all the exceptions derived from it.
        }catch (Annoyance e){
            print("Caught Annoyance");
        }

        try {
            throw new Sneeze();
        }catch (Annoyance e){
            print("Caught Annoyance");
        }
    }
}
class Annoyance extends Exception {}
class Sneeze extends Annoyance {}
