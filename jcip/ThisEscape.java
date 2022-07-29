package jcip;

/**
 * Implicitly causes the `this reference` to escape.
 * <br>
 * Do not escape this in the constructor.
 *
 * @author zqw
 * @date 2021/4/8
 */
class ThisEscape {
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
        /**
         * 注册监听器
         * @param e EventListener
         */
        void registerListener(EventListener e);
    }

    @FunctionalInterface
    interface EventListener {
        /**
         * 事件监听
         * @param e Event
         */
        void onEvent(Event e);
    }

    interface Event {
    }

}
