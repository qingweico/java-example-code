package jvm;

import java.lang.instrument.Instrumentation;

/**
 *
 * Instrumentation 代理 {@link Instrumentation}
 * @author zqw
 * @date 2021/10/20
 * @see EmptyObject
 */
class InstrumentationAgent {
    private static Instrumentation globalInstrumentation;

    public static void premain(final String argentArgs, final Instrumentation inst) {
        globalInstrumentation = inst;
    }

    public static long getObjectSize(final Object object) {
        if (globalInstrumentation == null) {
            throw new IllegalStateException("Agent not initialized.");
        }
        return globalInstrumentation.getObjectSize(object);
    }
}
