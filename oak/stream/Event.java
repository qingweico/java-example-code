package oak.stream;

import java.util.stream.Stream;

/**
 * Monad
 *
 * @author zqw
 * @date 2021/9/27
 */
public class Event<T> {
    T data;

    public Event(T data) {
        this.data = data;
    }

    static class EventData {
        Integer id;
        String msg;

        public EventData(Integer id, String msg) {
            this.id = id;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "EventData{" + "id=" + id + ", msg='" + msg + '\'' + '}';
        }
    }

    static class Transforms {
        static EventData transform(Integer id) {
            EventData ed;
            switch (id) {
                case 0:
                    ed = new EventData(id, "Start");
                    break;
                case 1:
                    ed = new EventData(id, "Running");
                    break;
                case 2:
                    ed = new EventData(id, "Done");
                    break;
                case 3:
                    ed = new EventData(id, "Fail");
                    break;
                default:
                    ed = new EventData(id, "Error");
            }
            return ed;
        }
    }

    <B> Event<?> map(Fn<T, B> f) {
        return new Event<>(f.apply(this.data));
    }

    /**
     * monad
     *
     * @param <A>
     * @param <B>
     */
    @FunctionalInterface
    interface Fn<A, B> {
        /**
         * apply
         *
         * @param a A
         * @return B
         */
        B apply(A a);
    }

    public static void main(String[] args) {
        Stream<Event<Integer>> s = Stream.of(new Event<>(0), new Event<>(1), new Event<>(2));

        s.map(event -> event.map(Transforms::transform)).forEach((e) -> System.out.println(e.data));

    }
}
