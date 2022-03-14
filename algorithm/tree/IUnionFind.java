package algorithm.tree;

/**
 * @author zqw
 * @date 2021/11/15
 */
public interface IUnionFind {
    void union(int p, int q);
    boolean isConnected(int p, int q);
    int size();
}
