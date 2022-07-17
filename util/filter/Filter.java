package util.filter;

/**
 * {@link Filter} interface
 *
 * @param <T>
 *         the type of Filtered object
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 */
public interface Filter<T> {

    /**
     * Does accept filtered object?
     *
     * @param filteredObject
     *         filtered object
     * @return Accept filtered object or not.
     */
    boolean accept(T filteredObject);
}
