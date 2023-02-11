package coretech2.local;

/**
 * 使用汇编语言(如 X86_64 的 SIMD 指令)来提升关键代码的性能
 * 再比如, 希望调用 Java 核心类库无法提供的, 某个体系架构或者操作系统特有的功能
 * 在这种情况下, 往往会牺牲可移植性; 在 Java 代码中调用 C/C++ 代码, 并在其中实现所需功能;
 * 这种跨语言的调用, 便需要借助 Java 虚拟机的 Java Native Interface(JNI)机制;
 * {@link Object#hashCode()} 方法便是一个 native 方法, 它对应的 C 函数将计算对象的哈希值,
 * 并缓存在对象头、栈上锁记录(轻型锁)或对象监视锁(重型锁所使用的 monitor)中, 以确保该
 * 值在对象的生命周期之内不会变更
 *
 * @author zqw
 * @date 2022/8/9
 */
public class HelloNative {

    static int m = 10;

    public native void instance();

    public static native void greeting();

    public static native void greeting(long i);

    static {
        System.loadLibrary("HelloNative");
        System.out.println(m);
    }

    public static void main(String[] args) {
        HelloNative hn = new HelloNative();
        hn.instance();
        greeting();
        greeting(1);
    }
}


// [Windows Environment]
// Generate `h` header file: Root directory: javac -h coretech2/local/ coretech2/local/HelloNative.java
// gcc -I ${jdk_home}\include\win32 -LD HelloNative.c -FeHelloNative.dll
