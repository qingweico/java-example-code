package thread;

/**
 * {@code openjdk}
 * Thread native method see {@code Thread.c}
 * {@code jvm.cpp JavaThread(&thread_entry, sz)}
 * {@code thread.cpp#JavaThread::JavaThread(ThreadFunction entry_point, size_t stack_sz)#os::create_thread}
 * {@code os_linux.cpp#os::create_thread}
 * {@see pthread.c}
 * {@see thread_CustomThread.h}
 * {@see thread_new.c}
 * {@link UnsatisfiedLinkError}
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



