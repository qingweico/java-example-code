package jcip;

/**
 * Use the factory method to prevent this reference from escaping during construction.
 *
 * @author:qiming
 * @date: 2021/4/8
 */
public class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
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
