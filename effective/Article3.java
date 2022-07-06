package effective;


import util.Generator;

/**
 * 用私有构造器或者枚举类型强化Singleton属性
 *
 * @author zqw
 * @date 2021/3/19
 */
class Article3 {
    public static void main(String[] args) {
        Elvis elvis = Elvis.INSTANCE;
        elvis.leaveTheBuilding();
        Elvis0 elvis0 = Elvis0.getInstance();
        elvis0.leaveTheBuilding();
        Elvis2 elvis2 = Elvis2.INSTANCE;
        elvis2.leaveTheBuilding();
    }
}

/**
 * Singleton with public final field
 */
class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public void leaveTheBuilding() {
        System.out.println(INSTANCE);
    }

}

/**
 * Singleton with static factory
 */
class Elvis0 {
    private static final Elvis0 INSTANCE = new Elvis0();

    private Elvis0() {
    }

    public static Elvis0 getInstance() {
        return INSTANCE;
    }

    public void leaveTheBuilding() {
        System.out.println(INSTANCE);
    }
}

/**
 * Enum singleton - the preferred approach
 * Single-element enumerated types are often the best way to implement singletons.
 * This approach is not easy to use when a Singleton must extend a superclass rather
 * than an Enum, although an Enum can be used to implement the interface.
 */
enum Elvis2 implements Generator<Object> {
    /**
     * ~
     */
    INSTANCE;

    @Override
    public Object next() {
        return null;
    }

    public void leaveTheBuilding() {
        System.out.println(INSTANCE);
    }
}