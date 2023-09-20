package jvm;

import util.Print;

/**
 * Java 虚拟机运行时内存空间大小
 * @author zqw
 * @date 2021/2/8
 */
class HeapSize {
    public static void main(String[] args) {
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        Print.println("initialMemory: " + initialMemory + "M");
        Print.println("maxMemory: " + maxMemory + "M");

        Print.println("The system memory size is " + initialMemory * 64.0 / 1024 + "G");
        Print.println("The system memory size is " + maxMemory * 4.0 / 1024 + "G");
    }
}
