package effective;


import util.Generator;

/**
 * 用私有构造器或者枚举类型强化Singleton属性
 *
 * @author:qiming
 * @date: 2021/3/19
 */
public class Article3 {
}

// Singleton with public final field
class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public void leaveTheBuilding() {
    }

}

// Singleton with static factory
class Elvis0 {
    private static final Elvis0 INSTANCE = new Elvis0();

    private Elvis0() {
    }

    public static Elvis0 getInstance() {
        return INSTANCE;
    }

    public void leaveTheBuilding() {
    }


}

// Enum singleton - the preferred approach
// Single-element enumerated types are often the best way to implement singletons.
// This approach is not easy to use when a Singleton must extend a superclass rather
// than an Enum, although an Enum can be used to implement the interface.
enum Elvis2 implements Generator<Object> {
    INSTANCE;

    public void leaveTheBuilding() {
    }

    @Override
    public Object next() {
        return null;
    }
}