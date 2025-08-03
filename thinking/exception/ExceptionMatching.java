package thinking.exception;

import cn.qingweico.io.Print;

/**
 * Catch exception hierarchies
 *
 * @author zqw
 * @date 2021/1/19
 */
public class ExceptionMatching {

    public static void main(String[] args) {
        // Catch the exact exception
        try {
           throw new Sneeze();
        }catch (Sneeze e){
            Print.println("Caught sneeze");
            // catch (Annoyance e) catches Annoyance and all the exceptions derived from it.
        }catch (AnnoyanceException e){
            Print.println("Caught Annoyance");
        }

        try {
            throw new Sneeze();
        }catch (AnnoyanceException e){
            Print.println("Caught Annoyance");
        }
    }
}
class AnnoyanceException extends Exception {}
class Sneeze extends AnnoyanceException {}
