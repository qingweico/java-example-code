package util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Management Utility class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @date 2022/7/10
 */
public final class ManagementUtils {
    private ManagementUtils() {
    }

    /**
     * {@link RuntimeMXBean}
     */
    private final static RuntimeMXBean RUNTIME_MX_BEAN = ManagementFactory.getRuntimeMXBean();

    /**
     * "jvm" Field name
     */
    private final static String JVM_FIELD_NAME = "jvm";
    /**
     * sun.management.ManagementFactory.jvm
     */
    final static Object JVM = initJvm();
    /**
     * "getProcessId" Method name
     */
    private final static String GET_PROCESS_ID_METHOD_NAME = "getProcessId";
    /**
     * "getProcessId" Method
     */
    final static Method GET_PROCESS_ID_METHOD = initGetProcessIdMethod();


    private static Object initJvm() {
        Object jvm = null;
        Field jvmField;
        if (RUNTIME_MX_BEAN != null) {
            try {
                jvmField = RUNTIME_MX_BEAN.getClass().getDeclaredField(JVM_FIELD_NAME);
                jvmField.setAccessible(true);
                jvm = jvmField.get(RUNTIME_MX_BEAN);
            } catch (Exception ignored) {
                System.err.printf("The Field[name : %s] can't be found in RuntimeMXBean Class[%s]!\n", JVM_FIELD_NAME, RUNTIME_MX_BEAN.getClass());
            }
        }
        return jvm;
    }

    private static Method initGetProcessIdMethod() {
        Class<?> jvmClass = JVM.getClass();
        Method getProcessIdMethod = null;
        try {
            getProcessIdMethod = jvmClass.getDeclaredMethod(GET_PROCESS_ID_METHOD_NAME);
            getProcessIdMethod.setAccessible(true);
        } catch (Exception ignored) {
            System.err.printf("%s method can't be found in class[%s]!\n", GET_PROCESS_ID_METHOD_NAME, jvmClass.getName());
        }
        return getProcessIdMethod;
    }

    private static Object invoke(Object... arguments) {
        Object result = null;
        Method method = ManagementUtils.GET_PROCESS_ID_METHOD;
        Object jvmObject = ManagementUtils.JVM;
        try {
            if (!method.canAccess(jvmObject)) {
                ManagementUtils.GET_PROCESS_ID_METHOD.setAccessible(true);
            }
            result = method.invoke(jvmObject, arguments);
        } catch (Exception ignored) {
            System.err.printf("%s method[arguments : %s] can't be invoked in object[%s]!\n",
                    method.getName(), Arrays.asList(arguments), jvmObject);
        }

        return result;
    }

    /**
     * Get the process ID of current JVM
     *
     * @return If you can't get the process ID , return <code>-1</code>
     */
    public static int getCurrentProcessId() {
        int processId = -1;
        Object result = invoke();
        if (result instanceof Integer) {
            processId = (Integer) result;
        } else {
            // no guarantee
            String name = RUNTIME_MX_BEAN.getName();
            String processIdValue = StringUtils.substringBefore(name, "@");
            if (NumberUtils.isNumber(processIdValue)) {
                processId = Integer.parseInt(processIdValue);
            }
        }
        return processId;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentProcessId());
    }
}
