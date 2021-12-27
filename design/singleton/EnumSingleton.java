package design.singleton;

/**
 * 枚举单例
 * 防止序列化以及线程安全
 * @author:qiming
 * @date: 2021/12/21
 */
public enum EnumSingleton {
    INSTANCE();

    EnumSingleton() {

    }


    public static void main(String[] args) {

    }
}
