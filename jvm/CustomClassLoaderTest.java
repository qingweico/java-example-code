package jvm;

import javassist.*;
import org.junit.Test;
import util.Print;
import util.constants.Constants;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Break Parents Delegation Model。
 *
 * @author zqw
 * @date 2021/10/21
 */
public class CustomClassLoaderTest {
    static String className = "jvm.GenClass";

    private static byte[] genClass() throws CannotCompileException, IOException {
        // javassist
        var classPool = ClassPool.getDefault();
        var ctClass = classPool.getOrNull(className);
        if (ctClass != null) {
            ctClass.defrost();
        }
        ctClass = classPool.makeClass(className);
        var method = new CtMethod(CtClass.voidType, "genMethod", new CtClass[0], ctClass);
        method.setModifiers(Modifier.PUBLIC);
        method.setBody("{System.out.println(\"" + className + "\");}");
        ctClass.addMethod(method);
        return ctClass.toBytecode();
    }

    static class BinClassLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (className.equals(name)) {
                try {
                    return defineClass(className, genClass(), 0, genClass().length);
                } catch (CannotCompileException | IOException e) {
                    Print.err(e.getMessage());
                }

            }
            return super.findClass(name);
        }
    }

    static class NetClassLoader extends ClassLoader {

        public NetClassLoader() throws IOException {
            this.connect();
        }

        byte[] bytes;

        private void connect() throws IOException {
            try (var socket = new Socket(Constants.LOOP_BACK, Constants.DEFAULT_COMMON_PORT)) {
                bytes = socket.getInputStream().readAllBytes();
            }
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (className.equals(name)) {
                try {
                    return defineClass(className, genClass(), 0, genClass().length);
                } catch (CannotCompileException | IOException e) {
                    Print.err(e.getMessage());
                }

            }
            return super.findClass(name);
        }

    }

    @Test
    public void testBinClassLoader() throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        var binClassLoader = new BinClassLoader();
        var cls = binClassLoader.loadClass(className);
        var inst = cls.getConstructor().newInstance();
        inst.getClass().getMethod("genMethod").invoke(inst);
    }

    @Test
    public void testNetClassLoader() throws IOException,
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        var netClassLoader = new NetClassLoader();
        var cls = netClassLoader.loadClass(className);
        var inst = cls.getConstructor().newInstance();
        inst.getClass().getMethod("genMethod").invoke(inst);
    }

    @Test
    public void serve() throws IOException, CannotCompileException {
        var serverSocket = new ServerSocket(Constants.DEFAULT_COMMON_PORT);
        serverSocket.setSoTimeout(10000);
        var bytes = genClass();
        while (true) {
            try (var client = serverSocket.accept()) {
                System.out.println("Accepting request...");
                var out = client.getOutputStream();
                out.write(bytes);
                out.flush();
            } catch (SocketTimeoutException e) {
                System.out.println("socket time out!");
                serverSocket.close();
                break;
            }
        }
    }
}
