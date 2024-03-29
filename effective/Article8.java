package effective;

import annotation.Pass;

import java.lang.ref.Cleaner;

/**
 * Finalization methods are often unpredictable, dangerous, and generally unnecessary.
 * 避免使用终结方法和清除方法
 *
 * @author zqw
 * @date 2021/4/1
 */
@Pass
class Article8 {
    public static void main(String[] args) {
        // They do increase the chance that a finalization or cleanup will be executed,
        // but they do not guarantee that a finalization or cleanup will be executed.
        System.runFinalization();
        System.gc();
    }
}

/**
 * An autocloseable class using a cleaner as a safety net.
 */
class Room implements AutoCloseable {

    private static final Cleaner CLEANER = Cleaner.create();

    /**
     * Resource that requires cleaning. Must not refer to Room!
     */
    private static class State implements Runnable {
        // Number of junk piles in this room.
        int numJunkPiles;

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }


        // Invoked by close method or cleaner.
        @Override
        public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0;
        }
    }

    private final Cleaner.Cleanable cleanable;

    public Room(int numJunkPiles) {
        // The state of the room, shared with our cleanable.
        State state = new State(numJunkPiles);
        cleanable = CLEANER.register(this, state);

    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}

class Adult {
    public static void main(String[] args) throws Exception {
        try (Room room = new Room(7)) {
            room.close();
            System.out.println("GoodBye");
        }
    }
}

class Teenager {
    public static void main(String[] args) {
        try (Room room = new Room(99)) {
            room.close();
            // "Cleaning room" will not be printed if there is no System.GC().
            System.gc();
            System.out.println("Peace out");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
