package design.behaviour.template;

import util.Print;

/**
 * 模板模式(Template Pattern)是一种行为设计模式, 它允许你定义一个算法的框架, 但将一些步骤的实现延迟到子类中
 * 优点 :
 * 代码复用 : 避免了重复实现相同的算法结构
 * 扩展性 : 通过添加新的子类来扩展算法的具体步骤 而不会影响到算法的整体结构
 * 提高代码的可维护性 : 将算法的不同部分分离开来 使得代码更易于维护和理解
 * 缺点 :
 * 每一个不同的实现都需要一个子类来实现, 会导致类的数量增加
 *
 * @author zqw
 * @date 2023/10/13
 */
public class Starter {

    public static void main(String[] args) {
        Sync nonFairSync = new NonFairSync();
        nonFairSync.lock();
        nonFairSync.unlock();
        Print.println("--------------------------------");
        Sync fairSync = new FairSync();
        fairSync.lock();
        fairSync.unlock();
    }
}
