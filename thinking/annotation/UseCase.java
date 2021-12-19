package thinking.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations that have no elements are called marker annotations
 * @see thinking.atunit.Test
 * The data types available for annotation elements:
 *      Basic data type
 *      String
 *      Class
 *      enum
 *      Annotation
 *      An array of the above data types
 *
 * @author:qiming
 * @date: 2021/4/8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
     int id();
     String description() default "no description";
}
