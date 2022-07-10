package jvm;

/**
 * Graal 是用 Java 写的即时编译器(与C2编译器最为明显的区别就是C2用C++写的) 从 Java9u 引入到JDK中,作为实验性质的即时编译器
 * 使用 JVM 参数{@code -XX:+UnlockExperimentalVMOptions(解锁实验参数) -XX:+UseJVMCICompiler} 启用
 * 将替换 Hotpot中的 C2 编译器,并响应原来由 C2 负责的编译请求
 * java {@code -XX:+PrintCommandLineFlags(打印命令行参数)} -version
 * 即时编译器与Java虚拟机交互过程
 * 1: 响应编译请求
 * 2: 获取编译所需的元数据(如类 方法 字段) 和反映程序执行状态的 profile
 * 3: 将生成的二进制代码部署至代码缓存里
 * <p>
 * 传统情况下, 即时编译器是与Java虚拟机紧耦合的, 对即时编译器的更改需要重新编译整个Java虚拟机
 * 为了让Java虚拟机与Graal解耦合, 引入Java虚拟机编译接口(JVM Compiler Interface JVMCI), 将以上三个功能抽象成一个Java层面的接口
 * 在 Graal 所依赖的 JVMCI 版本不变的情况下,仅仅需要替换 Graal 编译器相关的 jar包(Java9以后的jmod文件), 就可完成对Graal的升级
 *
 * @author zqw
 * @date 2022/7/2
 */
public class Graal {
    public static void main(String[] args) {
        System.out.println("Graal VM");
    }
}
