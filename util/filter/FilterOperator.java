package util.filter;

import java.lang.reflect.Array;

/**
 * {@link Filter} Operator enumeration, which contains {@link #AND}{@link #OR}{@link #XOR}
 *
 * @author zqw
 * @see Filter
 * @see #AND
 * @see #OR
 * @see #XOR
 */
public enum FilterOperator {

    /**
     * &
     */
    AND {
        @SafeVarargs
        @Override
        public final <T> boolean accept(T filteredObject, Filter<T>... filters) {
            int length = Array.getLength(filters);
            if (length == 0) {
                return true;
            }
            boolean success = true;
            for (Filter<T> filter : filters) {
                // 必须全部接受
                success &= filter.accept(filteredObject);
            }
            return success;
        }

    },
    /**
     * |
     */
    OR {
        @SafeVarargs
        @Override
        public final <T> boolean accept(T filteredObject, Filter<T>... filters) {
            int length = Array.getLength(filters);
            if (length == 0) {
                return true;
            }
            boolean success = false;
            for (Filter<T> filter : filters) {
                // 有一个就行
                success |= filter.accept(filteredObject);
            }
            return success;
        }
    },
    /**
     * XOR
     */
    XOR {
        @SafeVarargs
        @Override
        public final <T> boolean accept(T filteredObject, Filter<T>... filters) {
            int length = Array.getLength(filters);
            if (length == 0) {
                return true;
            }
            boolean success = true;
            for (Filter<T> filter : filters) {
                success ^= filter.accept(filteredObject);
            }
            return success;
        }
    };

    /**
     * multiple {@link Filter} accept
     *
     * @param <T>            Filtered object type
     * @param filteredObject Filtered object
     * @param filters        multiple {@link Filter}
     * @return If accepted, return <code>true</code>
     */
    public abstract <T> boolean accept(T filteredObject, Filter<T>... filters);

    /**
     * Create a combined {@link Filter} from multiple filters
     *
     * @param filters multiple filters
     * @return a combined {@link Filter}
     */
    @SafeVarargs
    public final <T> Filter<T> createFilter(final Filter<T>... filters) {
        final FilterOperator self = this;
        return filteredObject -> self.accept(filteredObject, filters);
    }

}
