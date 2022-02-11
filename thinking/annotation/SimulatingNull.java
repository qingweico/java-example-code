package thinking.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Elements cannot have indeterminate values
 *
 *
 * The element either has a default value,
 * or the element's value is supplied when the annotation is used.
 *
 * @author zqw
 * @date 2021/4/8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimulatingNull {
    // Customize some special values to indicate that an element does not exist.
    int id() default -1;
    String description() default "";
}
