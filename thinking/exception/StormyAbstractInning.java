package thinking.exception;

import static util.Print.print;

/**
 * Override methods may throw only exceptions specified in their
 * base-class version, or exception derived from base-class exception.
 *
 * @author zqw
 * @date 2021/1/17
 */
class StormyAbstractInning extends AbstractInning implements Storm {

    // Ok to add new exceptions for constructors, but you must deal with the base constructor exception.

    public StormyAbstractInning() throws RainedOut, BaseballException {
    }

    public StormyAbstractInning(String s) throws Foul, BaseballException {
    }

    // Regular methods must conform to base class

    // 编译错误 因为父类中work并没有抛出异常
    // @Override
    // public void work() throws PopFoul{}

    // Interface cannot add exceptions to existing methods from the base class.


//     @Override
//     public void event() throws RainedOut {}


    // If the method doesn't already exist in the base class, the exception is ok.

    @Override
    public void rainHard() throws RainedOut {

    }

    // Yon can choose to not throw any exceptions, even if the base version does.

    @Override
    public void event() {
    }

    // Override methods can throw inherited exceptions.

    @Override
    public void atBat() throws PopFoul {

    }

    public static void main(String[] args) {
        try {
            StormyAbstractInning si = new StormyAbstractInning();
            si.atBat();
        } catch (PopFoul e) {
            print("Pop foul");
        } catch (RainedOut e) {
            print("Rained out");
        } catch (BaseballException e) {
            print("Generic baseball exception");
        }

        // Strikes not throw in derived version.

        try {
            // What happen if you upcast?
            AbstractInning i = new StormyAbstractInning();
            i.atBat();
            // You must catch the exception from the base-class version of the method.
        } catch (Strike e) {
            print("Strike");
        } catch (Foul e) {
            print("Foul");
        } catch (RainedOut e) {
            print("Rained out");
        } catch (BaseballException e) {
            print("Generic baseball exception");
        }
    }
}

class BaseballException extends Exception {

}

class Foul extends BaseballException {
}

class Strike extends BaseballException {
}

abstract class AbstractInning {
    public AbstractInning() throws BaseballException {
    }

    public void event() throws BaseballException {
    }

    public abstract void atBat() throws Foul, Strike;

    public void work() {
    }
}

class StormException extends Exception {
}

class RainedOut extends StormException {
}

class PopFoul extends Foul {
}

interface Storm {
    /**
     * event
     *
     * @throws RainedOut RainedOut{@code RainedOutException}
     */
    void event() throws RainedOut;

    /**
     * rainHard
     * @throws RainedOut RainedOut RainedOut{@code RainedOutException}
     */
    void rainHard() throws RainedOut;
}
