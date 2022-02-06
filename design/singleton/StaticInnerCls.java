package design.singleton;

/**
 * 静态内部类
 * JVM保证单例
 * 加载外部类时不会加载内部类, 从而实现懒加载
 *
 * @author zqw
 * @date 2021/12/21
 */
public class StaticInnerCls {
    private static long id = 0;

    @Override
    public String toString() {
        return "[ StaticInnerCls id = " + id + " ]";
    }

    private StaticInnerCls() {
        id++;
    }

    private static class InstanceHolder {
        private static final StaticInnerCls INSTANCE = new StaticInnerCls();
    }

    public static StaticInnerCls getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static void main(String[] args) {

    }
}
