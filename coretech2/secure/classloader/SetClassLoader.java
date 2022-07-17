package coretech2.secure.classloader;

/**
 * @author zqw
 * @date 2022/7/14
 */
class SetClassLoader {

    static {
        System.out.println("SetClassLoader");
    }

    private final ClassLoader classLoader = this.getClass().getClassLoader();

    public static void main(String[] args) throws ClassNotFoundException {
        Thread t = Thread.currentThread();
        SetClassLoader setClassLoader = new SetClassLoader();
        // 每个线程都有一个对类加载器的引用 称为上下文类加载
        // 主线程的上下文类加载器是系统类加载器
        // 如果不做任何处理 那么所有线程都会将它们的上下文类加载器设置为系统类加载器
        // 通过调用 setContextClassLoader 可以设置为任何加载器
        t.setContextClassLoader(setClassLoader.classLoader);
        ClassLoader loader = t.getContextClassLoader();
        loader.loadClass("coretech2.secure.classloader.SetClassLoader");
    }
}
