package util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * Reflection Utility class, generic methods are defined from {@link FieldUtils},
 * {@link MethodUtils}, {@link ConstructorUtils}
 *
 * @author zqw
 * @date 2022/7/10
 * @see Method
 * @see Field
 * @see Constructor
 * @see Array
 * @see MethodUtils
 * @see FieldUtils
 * @see ConstructorUtils
 */
public class ReflectionUtils {

    /**
     * non-instantiability
     */
    private ReflectionUtils() {
    }

    /**
     * Sun JDK 实现类: sun.reflect.Reflection全名称
     */
    public static final String SUN_REFLECT_REFLECTION_CLASS_NAME = "sun.reflect.Reflection";

    /**
     * Current Type
     */
    private static final Class<?> TYPE = ReflectionUtils.class;
    /**
     * sun.reflect.Reflection方法名称
     */
    private static final String GET_CALLER_CLASS_METHOD_NAME = "getCallerClass";
    /**
     * sun.reflect.Reflection invocation frame
     */
    private static final int SUN_REFLECT_REFLECTION_INVOCATION_FRAME;
    /**
     * {@link StackTraceElement} invocation frame
     */
    private static final int STACK_TRACE_ELEMENT_INVOCATION_FRAME;
    /**
     * Is Supported sun.reflect.Reflection ?
     */
    private static final boolean SUPPORTED_SUN_REFLECT_REFLECTION;
    /**
     * sun.reflect.Reflection#getCallerClass(int) method
     */
    private static final Method GET_CALLER_CLASS_METHOD;

    // Initialize sun.reflect.Reflection
    static {
        Method method;
        boolean supported;
        int invocationFrame = 0;
        int underJdk9 = 9;
        try {
            // Use sun.reflect.Reflection to calculate frame
            Class<?> type = Class.forName(SUN_REFLECT_REFLECTION_CLASS_NAME);
            method = type.getMethod(GET_CALLER_CLASS_METHOD_NAME, int.class);
            method.setAccessible(true);
            // Adapt SUN JDK, The value of invocation frame in JDK 6/7/8 may be different
            for (int i = 0; i < underJdk9; i++) {
                Class<?> callerClass = (Class<?>) method.invoke(null, i);
                if (TYPE.equals(callerClass)) {
                    invocationFrame = i;
                    break;
                }
            }
            supported = true;
        } catch (Exception e) {
            method = null;
            supported = false;
        }
        // set method info
        GET_CALLER_CLASS_METHOD = method;
        SUPPORTED_SUN_REFLECT_REFLECTION = supported;
        // getCallerClass() -> getCallerClass(int)
        // Plugs 1 , because Invocation getCallerClass() method was considered as increment invocation frame
        // Plugs 1 , because Invocation getCallerClass(int) method was considered as increment invocation frame
        SUN_REFLECT_REFLECTION_INVOCATION_FRAME = invocationFrame + 2;
    }

    // Initialize StackTraceElement
    static {
        int invocationFrame = 0;
        // Use java.lang.StackTraceElement to calculate frame
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            String className = stackTraceElement.getClassName();
            if (TYPE.getName().equals(className)) {
                break;
            }
            invocationFrame++;
        }
        // getCallerClass() -> getCallerClass(int)
        // Plugs 1 , because Invocation getCallerClass() method was considered as increment invocation frame
        // Plugs 1 , because Invocation getCallerClass(int) method was considered as increment invocation frame
        STACK_TRACE_ELEMENT_INVOCATION_FRAME = invocationFrame + 2;
    }

    /**
     * Get Caller class
     * {@code jdk.internal.reflect.Reflection#getCallerClass();}
     * @return 获取调用该方法的Class name
     */
    @Nonnull
    public static String getCallerClassName() {
        return getCallerClassName(SUN_REFLECT_REFLECTION_INVOCATION_FRAME);
    }
    /**
     * Get Caller Class name
     *
     * @param invocationFrame invocation frame
     * @return Class name under specified invocation frame
     * @throws IndexOutOfBoundsException 当<code>invocationFrame</code>数值为负数或者超出实际的层次
     * @see Thread#getStackTrace()
     */
    @Nonnull
    protected static String getCallerClassName(int invocationFrame) throws IndexOutOfBoundsException {
        if (SUPPORTED_SUN_REFLECT_REFLECTION) {
            Class<?> callerClass = getCallerClassInSunJvm(invocationFrame + 1);
            if (callerClass != null) {
                return callerClass.getName();
            }
        }
        return getCallerClassNameInGeneralJvm(invocationFrame + 1);
    }

    /**
     * 通用实现方式,获取调用类名
     *
     * @return 调用类名
     * @see #getCallerClassNameInGeneralJvm(int)
     */
    static String getCallerClassNameInGeneralJvm() {
        return getCallerClassNameInGeneralJvm(STACK_TRACE_ELEMENT_INVOCATION_FRAME);
    }

    /**
     * 通用实现方式,通过指定调用层次数值,获取调用类名
     *
     * @param invocationFrame invocation frame
     * @return specified invocation frame 类
     * @throws IndexOutOfBoundsException 当<code>invocationFrame</code>数值为负数或者超出实际的层次
     * @since 1.0.0 2012-3-15 下午02:43:47
     */
    static String getCallerClassNameInGeneralJvm(int invocationFrame) throws IndexOutOfBoundsException {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement targetStackTraceElement = elements[invocationFrame];
        return targetStackTraceElement.getClassName();
    }

    static Class<?> getCallerClassInSunJvm(int realFramesToSkip) throws UnsupportedOperationException {
        if (!SUPPORTED_SUN_REFLECT_REFLECTION) {
            throw new UnsupportedOperationException("需要SUN的JVM!");
        }
        Class<?> callerClass = null;
        if (GET_CALLER_CLASS_METHOD != null) {
            try {
                callerClass = (Class<?>) GET_CALLER_CLASS_METHOD.invoke(null, realFramesToSkip);
            } catch (Exception ignored) {
            }
        }
        return callerClass;
    }

    /**
     * Get caller class in General JVM
     *
     * @param invocationFrame invocation frame
     * @return caller class
     * @see #getCallerClassNameInGeneralJvm(int)
     */
    static Class<?> getCallerClassInGeneralJvm(int invocationFrame) {
        String className = getCallerClassNameInGeneralJvm(invocationFrame + 1);
        Class<?> targetClass;
        try {
            targetClass = Class.forName(className);
        } catch (ClassNotFoundException impossibleException) {
            throw new IllegalStateException("How can?");
        }
        return targetClass;
    }

    /**
     * Get caller class
     * <p/>
     * For instance,
     * <pre>
     *     package com.acme;
     *     import ...;
     *     class Foo {
     *         public void bar(){
     *
     *         }
     *     }
     * </pre>
     *
     * @return Get caller class
     * @throws IllegalStateException 无法找到调用类时
     */
    @Nonnull
    public static Class<?> getCallerClass() throws IllegalStateException {
        return getCallerClass(SUN_REFLECT_REFLECTION_INVOCATION_FRAME);
    }

    /**
     * Get caller class In SUN HotSpot JVM
     *
     * @return Caller Class
     * @throws UnsupportedOperationException If JRE is not a SUN HotSpot JVM
     * @see #getCallerClassInSunJvm(int)
     */
    static Class<?> getCallerClassInSunJvm() throws UnsupportedOperationException {
        return getCallerClassInSunJvm(SUN_REFLECT_REFLECTION_INVOCATION_FRAME);
    }

    /**
     * Get caller class name In SUN HotSpot JVM
     *
     * @return Caller Class
     * @throws UnsupportedOperationException If JRE is not a SUN HotSpot JVM
     * @see #getCallerClassInSunJvm(int)
     */
    static String getCallerClassNameInSunJvm() throws UnsupportedOperationException {
        Class<?> callerClass = getCallerClassInSunJvm(SUN_REFLECT_REFLECTION_INVOCATION_FRAME);
        return callerClass.getName();
    }

    /**
     * @param invocationFrame invocation frame
     * @return Caller Class
     */
    static Class<?> getCallerClass(int invocationFrame) {
        if (SUPPORTED_SUN_REFLECT_REFLECTION) {
            Class<?> callerClass = getCallerClassInSunJvm(invocationFrame + 1);
            if (callerClass != null) {
                return callerClass;
            }
        }
        return getCallerClassInGeneralJvm(invocationFrame + 1);
    }

    /**
     * Get caller class in General JVM
     *
     * @return Caller Class
     * @see #getCallerClassInGeneralJvm(int)
     */
    static Class<?> getCallerClassInGeneralJvm() {
        return getCallerClassInGeneralJvm(STACK_TRACE_ELEMENT_INVOCATION_FRAME);
    }

    /**
     * Get caller class's {@link Package}
     *
     * @return caller class's {@link Package}
     * @throws IllegalStateException see {@link #getCallerClass()}
     * @see #getCallerClass()
     */
    public static Package getCallerPackage() throws IllegalStateException {
        return getCallerClass().getPackage();
    }

    /**
     * Assert array index
     *
     * @param array Array object
     * @param index index
     * @throws IllegalArgumentException       see {@link ReflectionUtils#assertArrayType(Object)}
     * @throws ArrayIndexOutOfBoundsException If <code>index</code> is less than 0 or equals or greater than length of array
     */
    public static void assertArrayIndex(Object array, int index) throws IllegalArgumentException {
        if (index < 0) {
            String message = String.format("The index argument must be positive , actual is %s", index);
            throw new ArrayIndexOutOfBoundsException(message);
        }
        ReflectionUtils.assertArrayType(array);
        int length = Array.getLength(array);
        if (index > length - 1) {
            String message = String.format("The index must be less than %s , actual is %s", length, index);
            throw new ArrayIndexOutOfBoundsException(message);
        }
    }

    /**
     * Assert the object is array or not
     *
     * @param array asserted object
     * @throws IllegalArgumentException if the object is not a array
     */
    public static void assertArrayType(Object array) throws IllegalArgumentException {
        Class<?> type = array.getClass();
        if (!type.isArray()) {
            String message = String.format("The argument is not an array object, its type is %s", type.getName());
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert Field type match
     *
     * @param object       Object
     * @param fieldName    field name
     * @param expectedType expected type
     * @throws IllegalArgumentException if type is not matched
     */
    public static void assertFieldMatchType(Object object, String fieldName, Class<?> expectedType) throws IllegalArgumentException {
        Class<?> type = object.getClass();
        Field field = FieldUtils.getDeclaredField(type, fieldName, true);
        Class<?> fieldType = field.getType();
        if (!expectedType.isAssignableFrom(fieldType)) {
            String message = String.format("The type[%s] of field[%s] in Class[%s] can't match expected type[%s]", fieldType.getName(), fieldName, type.getName(), expectedType.getName());
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * Convert {@link Array} object to {@link List}
     *
     * @param array array object
     * @return {@link List}
     * @throws IllegalArgumentException if the object argument is not an array
     */
    @Nonnull
    public static <T> List<T> toList(Object array) throws IllegalArgumentException {
        int length = Array.getLength(array);
        List<T> list = Lists.newArrayListWithCapacity(length);
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            @SuppressWarnings("unchecked") T t = (T) toObject(element);
            list.add(t);
        }
        return list;
    }

    private static Object toObject(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> type = object.getClass();
        if (type.isArray()) {
            return toList(object);
        } else {
            return object;
        }
    }


    /**
     * Read fields value as {@link Map}
     *
     * @param object object to be read
     * @return fields value as {@link Map}
     */
    @Nonnull
    public static Map<String, Object> readFieldsAsMap(Object object) {
        Map<String, Object> fieldsAsMap = Maps.newLinkedHashMap();
        Class<?> type = object.getClass();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {

            // To filter static fields
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);

            try {
                String fieldName = field.getName();
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    Class<?> fieldValueType = fieldValue.getClass();
                    if (ClassUtils.isPrimitiveOrWrapper(fieldValueType)) {
                        System.out.printf("fieldValueType: [%s] isPrimitiveOrWrapper%n", fieldValueType);
                    } else if (fieldValueType.isArray()) {
                        fieldValue = toList(fieldValue);
                    } else if ("java.lang".equals(fieldValueType.getPackage().getName())) {
                        System.out.printf("fieldValueType [%s] class name start with java.lang%n", fieldValueType);
                    } else {
                        fieldValue = readFieldsAsMap(fieldValue);
                    }
                }
                fieldsAsMap.put(fieldName, fieldValue);
            } catch (IllegalAccessException e) {
                Print.err(e.getMessage());
            }
        }
        return fieldsAsMap;
    }
}
