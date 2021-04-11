package thinking.atunit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Test tag
 *
 * @author:qiming
 * @date: 2021/4/8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    /*
     @Target
     Where will the definition annotations be used.
     Possible elementType parameters include:
         CONSTRUCTOR: The declaration of the constructor
         FIELD: Domain declarations (including instances of enum)
         LOCAL_VARIABLES: Local variable declaration
         METHOD Method declaration
         PACKAGE: Package declaration
         PARAMETER: Parameter declaration
         TYPE: Class, interface (including annotation types), or enum declaration

     @Retention
     At what level is the definition of the annotation available.
     Possible RetentionPolicy parameters include:
         source code(SOURCE): Annotations are discarded by the compiler.
         class file(CLASS): Annotations are available in class files, but are discarded by the VM.
         running(RUNNING): The VM will also keep the annotations during run time,
         so the information about the annotations can be read through reflection.


     @Documented: Include this annotation in the Javadoc.
     @Inherited: Allows a subclass to inherit annotations from its parent class.
     */
}
