package util.filter;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * {@link Filter} utility class
 *
 * @author zqw
 */
public class FilterUtils {

    private FilterUtils() {
    }

    /**
     * Filter {@link Iterable} object to List
     *
     * @param iterable
     *         {@link Iterable} object
     * @param filter
     *         {@link Filter} object
     * @param <E>
     *         The filtered object type
     * @return List<E>
     */
    @Nonnull
    public static <E> List<E> filter(Iterable<E> iterable, Filter<E> filter) {
        return filter(iterable, FilterOperator.AND, filter);
    }

    /**
     * Filter {@link Iterable} object to List
     *
     * @param iterable
     *         {@link Iterable} object
     * @param filterOperator
     *         {@link FilterOperator}
     * @param filters
     *         {@link Filter} array objects
     * @param <E>
     *         The filtered object type
     * @return List<E>
     */
    @SafeVarargs
    @Nonnull
    public static <E> List<E> filter(Iterable<E> iterable, FilterOperator filterOperator, Filter<E> ...filters) {
        List<E> list = Lists.newArrayList(iterable);
        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (!filterOperator.accept(element, filters)) {
                iterator.remove();
            }
        }
        return Collections.unmodifiableList(list);
    }
}
