package util.filter.test;

import junit.framework.TestCase;
import org.junit.Ignore;

import java.util.Iterator;

/**
 * Abstract {@link TestCase}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 */
@Ignore
public abstract class AbstractTestCase {

    protected final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public void echo(Object object, Object... others) {
        echo(object);
        echo(others);
    }

    public void echo(Object object) {
        System.out.println(object);
    }

    public void echo(Iterable<?> iterable) {
        Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            echo(iterator.next());
        }
    }
}
