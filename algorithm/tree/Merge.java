package algorithm.tree;

/**
 * @author zqw
 * @date 2021/11/1
 */
@FunctionalInterface
public interface Merge <E>{
   E merge(E a, E b);
}
