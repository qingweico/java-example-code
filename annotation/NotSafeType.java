package annotation;

import java.lang.annotation.*;

/**
 * The class to which this annotation is applied is not type-safe.
 * @author zqw
 * @date 2021/11/18
 * @see SafeType
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSafeType {
}
