package jvm;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.jar.asm.Opcodes;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author zqw
 * @date 2025/10/29
 */
@Slf4j
class Asm {
    public static byte[] generateClass() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        cw.visit(Opcodes.V17, Opcodes.ACC_PUBLIC, "GeneratedByAsm", null, "java/lang/Object", null);


        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();

        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello, ASM!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();


        cw.visitEnd();

        return cw.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        byte[] classBytes = generateClass();

        FileCopyUtils.copy(classBytes, new File("GeneratedByAsm.class"));

        try {
            Class<?> generatedClass = new ClassLoader() {
                public Class<?> defineClass(byte[] bytes) {
                    return defineClass(null, bytes, 0, bytes.length);
                }
            }.defineClass(classBytes);
            Method mainMethod = generatedClass.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
