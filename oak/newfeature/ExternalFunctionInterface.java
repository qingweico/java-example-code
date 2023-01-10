package oak.newfeature;

import coretech2.local.HelloNative;
import jdk.incubator.foreign.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

/**
 * -------------------- 外部函数接口 --------------------
 * Java 的外部函数接口和外部内存接口一起,会极大地丰富 Java 语言的生态环境
 * 使用 Java 本地接口 (Java Native Interface, JNI) {@link HelloNative}
 * 使用 JNI 带来的问题
 * 1 : Java 本地接口实现的动态库是平台相关的,所以就没有了 Java 语言[一次编译,到处运行]的跨平台优势
 * 2 : 因为逃脱了 JVM 的语言安全机制, JNI 本质上是不安全的
 *
 * @author zqw
 * @date 2022/12/30
 * @see ResourceScope 定义了内存资源的生命周期管理机制
 * @see CLinker 实现了 C 语言的应用程序二进制接口(Application Binary Interface - ABI)的调用规则
 * @see FunctionDescriptor 描述了外部函数必须符合的规范
 * 这个接口的对象, 可以用来链接 C 语言实现的外部函数
 * Compile Args: --add-modules jdk.incubator.foreign
 * VM (Runtime) Args: --add-modules jdk.incubator.foreign --enable-native-access=ALL-UNNAMED
 */
class ExternalFunctionInterface {
    public static void main(String[] args) {
        try (ResourceScope scope = ResourceScope.newConfinedScope()) {
            CLinker cLinker = CLinker.getInstance();
            MemorySegment helloWorld =
                    CLinker.toCString("Hello, world!\n", scope);
            MethodHandle cPrintf = cLinker.downcallHandle(
                    // 使用 CLinker 的函数标志符(Symbol)查询功能,查找 C 语言定义的函数 printf
                    CLinker.systemLookup().lookup("printf").orElseThrow(),
                    MethodType.methodType(int.class, MemoryAddress.class),
                    FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER));
            cPrintf.invoke(helloWorld.address());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // note : 使用外部函数接口的代码,不再需要编写 C 代码;当然,也不再需要编译,链接生成 C 的动态库了
    // 所以,由动态库带来的平台相关的问题,也就不存在了

    // 从根本上说,任何 Java 代码和本地代码之间的交互,都会损害 Java 平台的完整性;链接到预编译的 C 函数,
    // 本质上是不可靠的;Java 运行时,无法保证 C 函数的签名和 Java 代码的期望是匹配的,其中一些可能会导致
    // JVM 崩溃的错误,这在 Java 运行时无法阻止,Java 代码也没有办法捕获,而使用 JNI 代码的本地代码则尤
    // 其危险,这样的代码,甚至可以访问 JDK 的内部,更改不可变数据的数值,允许本地代码绕过 Java 代码的安
    // 全机制,破坏了 Java 的安全性赖以存在的边界和假设;所以说,JNI 本质上是不安全的

    
    // 大部分外部函数接口的设计则是安全的 一般来说,使用外部函数接口的代码,不会导致 JVM 的崩溃 
    // 也有一部分外部函数接口是不安全的,但是这种不安全性并没有到达 JNI 那样的严重性,可以说,使
    // 用外部函数接口的代码是 Java 代码,因此也受到 Java 安全机制的约束
    
    // 外部函数接口正式发布后,JNI 的退出可能也就要提上议程了 
    // 在外部函数接口的提案里,我们可以看到这样的描述:
    // JNI 机制是如此危险,以至于我们希望库在安全和不安全操作中都更喜欢纯 Java 的外部函数接口,
    // 以便我们可以在默认情况下及时全面禁用 JNI 这与使 Java 平台开箱即用、缺省安全的更广泛的 Java 路线图是一致的
}
