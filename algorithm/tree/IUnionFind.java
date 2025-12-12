package algorithm.tree;

/**
 * @author zqw
 * @date 2021/11/15
 */
public interface IUnionFind {
    /**
     * IUnionFind union
     * @param p 要合并的元素或者元素的代表元
     * @param q 表示要合并的另一个元素或者元素的代表元
     */
    void union(int p, int q);

    /**
     * 判断两个节点是否连通
     * @param p 节点p
     * @param q 节点q
     * @return true 两个节点连通 || false 两个节点不连通
     */
    boolean isConnected(int p, int q);

    /**
     * 获取并查集中元素的总数量
     * @return 并查集中元素的总个数
     */
    int size();
}
