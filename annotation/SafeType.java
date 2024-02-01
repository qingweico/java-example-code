package annotation;

import java.lang.annotation.*;

/**
 * The class to which this annotation is applied is type-safe.
 *
 * @author zqw
 * @date 2021/11/18
 * @see NotSafeType
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SafeType {
}
