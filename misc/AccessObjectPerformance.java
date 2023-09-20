package misc;

import org.springframework.util.StopWatch;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * <a href="https://www.optaplanner.org/blog/2018/01/09/JavaReflectionButMuchFaster.html"></a>
 *
 * @author zqw
 * @date 2023/9/20
 */
public class AccessObjectPerformance {

    /*Direct access*/

    public void execGetterDirectAccess(Object object) {
        ((Person) object).getName();
    }

    public void execGetterReflect(Object bean) throws Exception {
        Method getterMethod = Person.class.getMethod("getName");
        getterMethod.setAccessible(true);
        getterMethod.invoke(bean);
    }

    public void execGetterMethodHandles(Object bean) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findVirtual(Person.class, "getName", MethodType.methodType(String.class))
                .asType(MethodType.methodType(void.class, Object.class));
        methodHandle.invokeExact(bean);
    }

    /**
     * @param bean Object
     * @see JavaFileObject
     * @see ToolProvider#getSystemJavaCompiler
     * @see JavaFileManager
     * TODO Using JavaCompiler
     * <a href="https://github.com/ge0ffrey/ge0ffrey-presentations/tree/master/code/fasterreflection"></a>
     */

    @SuppressWarnings("unused")
    public void execGetterJavaCompiler(Object bean) {}

    public void execSetterLambdaFactory(Object bean) throws Throwable{
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        CallSite callSite = LambdaMetafactory.metafactory(lookup, "apply",
                MethodType.methodType(Function.class),
                MethodType.methodType(Object.class, Object.class),
                lookup.findVirtual(Person.class, "getName", MethodType.methodType(String.class)),
                MethodType.methodType(String.class, Person.class));
        @SuppressWarnings("unchecked")
        Function<Object, Void> getterFunction = (Function<Object, Void>) callSite.getTarget().invokeExact();
        getterFunction.apply(bean);
    }

    public static void main(String[] args) throws Throwable {
        AccessObjectPerformance aop = new AccessObjectPerformance();
        StopWatch sw = new StopWatch("Access Object Performance");
        Person person = new Person();
        sw.start("DirectAccess");
        aop.execGetterDirectAccess(person);
        sw.stop();
        sw.start("Reflect");
        aop.execGetterReflect(person);
        sw.stop();
        sw.start("MethodHandles");
        aop.execGetterMethodHandles(person);
        sw.stop();
        sw.start("LambdaFactory");
        aop.execSetterLambdaFactory(person);
        sw.stop();
        System.out.println(sw.prettyPrint());
    }
}

class Person {
    public String getName() {
        return "Person";
    }
}
