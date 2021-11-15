package algorithm.tree;

/**
 * @author:qiming
 * @date: 2021/11/15
 */
public class UnionFind implements IUnionFind{
    private final int[] parent;
    /**
     * elSize[i] represents the number of elements in the set rooted at 'i'.
     */
    private final int[] elSize;
    // rank[i] represents the numbers of levels of the tree whose root node is 'i'.
    // private final int[] rank;
    public UnionFind(int size) {
        parent = new int[size];
        this.elSize = new int[size];
        for(int i = 0;i < size;i++) {
            parent[i] = i;
            this.elSize[i] = 1;
        }
    }

    public int find(int p) {
        if(p < 0 || p >= parent.length) {
            throw new IllegalArgumentException("p = " + p);
        }
        while ( p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;

    }
    @Override
    public void union(int p, int q) {
        p = find(p);
        q = find(q);
        if(p == q) {
            return;
        }
        if(elSize[q] < elSize[p]) {
            parent[q] = p;
            elSize[p] += elSize[q] ;
        }else {
            parent[p] = q;
            elSize[q] += elSize[p] ;
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int size() {
        return parent.length;
    }
}
