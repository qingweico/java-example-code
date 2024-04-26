package design.observer;

/**
 * @author zqw
 * @date 2022/3/2
 */
@SuppressWarnings("deprecation")
public interface Subject {
    /**
     * ~
     *
     * @param o an observer to be added.
     * @see java.util.Observable#addObserver
     */
    void registerObserver(Observer o);

    /**
     * ~
     *
     * @param o the observer to be deleted.
     * @see java.util.Observable#deleteObserver
     */

    void removeObserver(Observer o);

    /**
     * ~
     *
     * @see java.util.Observable#notifyObservers
     */
    void notifyObserver();
}
