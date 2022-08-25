package algorithm.tree;

/**
 * @author zqw
 * @date 2021/11/1
 */
@FunctionalInterface
public interface Merge <E>{
   /**
    * 两个元素的合并抽象动作
    * @param a e1
    * @param b e2
    * @return merge result
    */
   E merge(E a, E b);
}
