package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * =====================================================
 * Code has no serious errors and compiler warnings can
 * be ignored within predictive control, code test pass!
 * [I know what I'm doing!!!]
 * =====================================================
 *
 * @author zqw
 * @date 2022/7/20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Pass {
}
