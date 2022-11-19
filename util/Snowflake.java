package util;

/**
 * @author zqw
 * @date 2022/7/3
 */
public class Snowflake {
    private static final int NODE_SHIFT = 10;
    private static final int SEQ_SHIFT = 12;
    private static final short MAX_NODE = 1024;
    private static final short MAX_SEQUENCE = 4096;

    private short sequence;
    private long referenceTime;
    private final int node;

    /**
     * A snowflake is designed to operate as a singleton instance within the context of a node.
     * If you deploy different nodes, supplying a unique node id will guarantee the uniqueness
     * of ids generated concurrently on different nodes.
     *
     * @param node This is an id you use to differentiate different nodes.
     */
    public Snowflake(int node) {
        if (node < 0 || node > MAX_NODE) {
            throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, MAX_NODE));
        }
        this.node = node;
    }

    /**
     * Generates a k-ordered unique 64-bit integer. Subsequent invocations of this method will produce
     * increasing integer values.
     *
     * @return The next 64-bit integer.
     */
    public long next() {

        long currentTime = System.currentTimeMillis();
        long counter;

        synchronized (this) {

            if (currentTime < referenceTime) {
                throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s", referenceTime, currentTime));
            } else if (currentTime > referenceTime) {
                this.sequence = 0;
            } else {
                if (this.sequence < Snowflake.MAX_SEQUENCE) {
                    this.sequence++;
                } else {
                    throw new RuntimeException("Sequence exhausted at " + this.sequence);
                }
            }
            counter = this.sequence;
            referenceTime = currentTime;
        }

        return currentTime << NODE_SHIFT << SEQ_SHIFT | (long) node << SEQ_SHIFT | counter;
    }
}
