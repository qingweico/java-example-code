package thread;

/**
 * --------------------------------- 线程模型和协程 ---------------------------------
 * {@code openjdk}
 * Thread native method see {@code Thread.c}
 * {@code jvm.cpp JavaThread(&thread_entry, sz)}
 * {@code thread.cpp#JavaThread::JavaThread(ThreadFunction entry_point, size_t stack_sz)#os::create_thread}
 * {@code os_linux.cpp#os::create_thread}
 * {@see pthread.c}
 * {@see thread_CustomThread.h}
 * {@see thread_new.c}
 * {@link UnsatisfiedLinkError}
 * --------------------------------- fork --------------------------------------
 * 在 Linux 中通过fork()函数创建一个子进程来代表一个内核中的线程
 * 一个进程调用fork()函数后,系统先会给新的进程分配资源;例如,储存数据和代码的空间,然后把原来进程的所有
 * 值都复制到新的进程中,只有少数值与原来的值不同
 * --------------------------------- LWP ---------------------------------------
 * Light Weight Process
 * 相对于 fork()系统调用创建的线程来说,LWP 使用 clone() 系统调用创建线程,该函数是将部分父进程的
 * 资源的数据结构进行复制,复制内容可选,且没有被复制的资源可以通过指针共享给子进程;因此,轻量级进程的运
 * 行单元更小,运行速度更快;LWP 是和内核线程一对一映射的,每个LWP都是由一个内核线程支持
 * --------------------------------- clone -------------------------------------
 * 在 Linux 下 JVM Thread 的实现基于pthread_thread实现的, 而pthread_thread实际上是调用了
 * clone()完成系统调用创建线程的;目前 Java 在 Linux的 下采用的是用户线程加轻量级线程,一个用户线
 * 程映射到一个内核线程;由于线程是通过内核调度,所以从一个线程切换到另外一个线程就涉及到了上下文切换而
 * Go语言是使用了 N:M线程模型实现了自己的调度器,它在N个内核线程上多路复用M个协程,协程的上下文切换就
 * 是在用户态由协程调度器完成的,因此不需要陷入内核
 * --------------------------------- 协程 ---------------------------------------
 * 相比线程,协程少了由于同步资源竞争带来的 CPU 上下文切换,IO密集型的应用比较合适,特备是在网络请求中,
 * 有较多的时间在等待后端响应,协程可以保证线程不会阻塞在等待网络响应中,充分利用了多核多线程的能力,而对
 * 于 CPU 密集型的应用,由于在多数情况下 CPU 都比较繁忙,协程的优势就不是特别明显了
 * 协程之间依靠邮箱来进行通信和数据共享;协程与线程最大的不同就是,线程通过共享内存在实现数据共享;而协程
 * 是使用了通信的方式来实现了数据共享,主要就是为了避免内存共享数据带来的线程安全问题
 * 协程与线程的性能比较: {@link CoroutineCompareThread}
 * 协程和线程密切相关,协程可以认为是运行在线程上的代码块,协程提供的挂起操作会使协程暂停,而不会导致线程
 * 阻塞,协程的设计方式极大地提高了线程的使用率
 * <p>
 * --------------------------------- Java 协程框架 -------------------------------
 * {@code kilim}(<a href="https://github.com/kilim/kilim">...</a>)
 * {@code quasar}(<a href="https://github.com/quasarframework/quasar">...</a>)
 *
 * @author zqw
 * @date 2021/6/25
 */
class CustomThread {

    static {
        System.loadLibrary("CustomThreadNative");
    }

    public static void main(String[] args) {
        CustomThread ct = new CustomThread();
        ct.start0();
    }

    /**
     * Using JNI
     */
    private native void start0();

}


// /home/java (copy CustomThread.java to this directory, include the package name)


// 1: javah thread.CustomThread   -----> thread_CustomThread.h

// see thread_new.c
// 2: gcc -fPIC -I /usr/local/jdk8/include/linux -I /usr/local/jdk8/include/linux
//        -shared -o libCustomThreadNative.so thread_new.c   -----> libCustomThreadNative.so


// 3: export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/java

// 4: cd thread        javac CustomThread.java

// 5: cd ../           java thread.CustomThread



