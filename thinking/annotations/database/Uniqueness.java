package thinking.annotations.database;

/**
 * Sample of nested annotations
 *
 * @author:qiming
 * @date: 2021/4/8
 */
public @interface Uniqueness {
    // Default values for all elements in the Constraints annotation are used by default,
    // otherwise, please display the specified values.
    Constraints constraints() default @Constraints(
            unique = true,
            primaryKey = true,
            allowNull = false
    );
}
