package jcip;

/**
 * Implicitly causes the this reference to escape.
 * <br>
 * Do not escape this in the constructor.
 *
 * @author:qiming
 * @date: 2021/4/8
 */
public class ThisEscape {
    public ThisEscape(EventSource source) {
        // Publish an object that has not yet been constructed.
        source.registerListener(
                // ==> this::doSomething
                new EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        doSomething(e);
                    }
                }
        );
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }

}
