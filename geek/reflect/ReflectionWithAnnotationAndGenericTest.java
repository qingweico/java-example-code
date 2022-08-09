package geek.reflect;

import annotation.Pass;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zqw
 * @date 2022/8/5
 */
@Slf4j
@Pass
@SuppressWarnings("all")
public
class ReflectionWithAnnotationAndGenericTest {
    private void age(int age) {
        log.info("int age = {}", age);
    }

    private void age(Integer age) {
        log.info("Integer age = {}", age);
    }

    /*反射调用方法不是以传参决定重载*/

    @Test
    public void reloadingPassReflect() throws Exception {
        // 走 int 重载方法
        // 原因是Integer.TYPE 获取的是原生类型的int, 而通过反射调用方法时先获取方法签名,此时已确定入参的类型
        // 不再根据实际入参类型确定重载方法
        getClass().getDeclaredMethod("age", Integer.TYPE).invoke(this, Integer.valueOf("22"));
        // 执行的参数类型是Integer
        getClass().getDeclaredMethod("age", Integer.class).invoke(this, 22);
    }

    // ================================= 泛型经过类型擦除多出桥接方法的问题 =================================

    static class Parent<T> {
        AtomicInteger ai = new AtomicInteger();
        private T value;

        @Override
        public String toString() {
            return String.format("value: %s,No: %d", value, ai.get());
        }

        public void setValue(T value) {
            System.out.println("Parent setValue");
            this.value = value;
            ai.incrementAndGet();
        }
    }

    static class Child extends Parent {
        /*子类方法重写父类方法失败*/
        // 1: 父类的setValue参数类型为T,泛型擦除后为Object类型,子类的参数类型为String
        // 所以子类并没有重写setValue方法
        // 2: 子类方法没有加@Override注解,编译器没有检测到方法重写失败
        public void setValue(String value) {
            super.setValue(value);
            System.out.println("Child setValue");
        }
    }

    static class Son extends Parent<String> {
        @Override
        public void setValue(String value) {
            super.setValue(value);
            System.out.println("Child setValue");
        }
    }

    @Test
    public void son() {
        Son son = new Son();
        // resolving:
        Arrays.stream(son.getClass().getDeclaredMethods()).filter(
                // 由于[泛型类型擦除 T -> Object]编译器会在Son字节码中生成一个入参类型为Object的setValue桥接方法来保证
                // Java 语言层面重写的一致性;
                // 使用method.isBridge()过滤掉即可
                method -> "setValue".equals(method.getName()) && !method.isBridge())
                // 只希望匹配0个或者1个
                .findFirst().ifPresent(method -> {
                    try {
                        method.invoke(son, "son");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        System.out.println(son);
    }

    @Test
    public void genericTypeErasure() {

        Child child = new Child();
        // getMethods 可以获取子类和父类所有的[public]方法
        // getDeclaredMethod 只能获取当前类的所有方法
        Arrays.stream(child.getClass().getMethods())
                .filter(method -> "setValue".equals(method.getName()))
                .forEach(method -> {
                    try {
                        method.invoke(child, "child");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child);
    }
    // =============== 子类及子类的方法无法自动继承父类上以及父类方法上的注解 ===============

    // 使用 @Inherited 注解只能实现类上的注解 但是仍然无法获取父类方法上的注解
    // 使用 Spring AnnotatedElementUtils#findMergedAnnotation(AnnotatedElement element, Class<A> annotationType)
}
