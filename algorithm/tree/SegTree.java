package algorithm.tree;

/**
 * @author:qiming
 * @date: 2021/11/1
 */
public class SegTree<E> {
    public E[] tree;
    private final E[] data;
    private final Merge<E> merge;

    public int size() {
        return data.length;
    }

    @SuppressWarnings("unchecked")
    public SegTree(E[] arr, Merge<E> merge) {
        this.data = (E[]) new Object[arr.length];
        this.merge = merge;
        System.arraycopy(arr, 0, data, 0, arr.length);
        tree = (E[]) new Object[arr.length * 4];
        build(0, 0, data.length - 1);
    }

    private void build(int root, int l, int r) {
        if (l == r) {
            tree[root] = data[l];
            return;
        }
        int left = leftChild(root);
        int right = rightChild(root);
        int mid = l + ((r - l) >> 1);
        build(left, l, mid);
        build(right, mid + 1, r);
        tree[root] = merge.merge(tree[left], tree[right]);
    }

    public E query(int L, int R) {
        if (L < 0 || R < 0 || L > tree.length || R > tree.length) {
            throw new IllegalArgumentException();
        }
        return query(0, 0, data.length - 1, L, R);
    }

    private E query(int root, int l, int r, int L, int R) {
        if(l == L && r == R) {
            return tree[root];
        }
        int left = leftChild(root);
        int right = rightChild(root);
        int mid = l + ((r - l) >> 1);
        if(L >= mid + 1) {
            return query(right, mid + 1, r, L, R);
        }else if(R <= mid) {
            return query(left, l, mid, L, R);
        }
        E leftRes = query(left, l, mid, L, mid);
        E rightRes = query(right, mid + 1, r, mid + 1, R);
        return merge.merge(leftRes, rightRes);
    }

    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IllegalArgumentException("index = " + index);
        }
        return data[index];
    }
    public void set(int index, E e) {
        if (index < 0 || index >= size()) {
            throw new IllegalArgumentException("index = " + index);
        }
        set(0, 0, data.length - 1, index, e);
    }
    private void set(int root, int l ,int r, int index, E e) {
        if(l == r) {
            tree[root] = e;
            return;
        }
        int mid = l + ((r - l) >> 1);
        int left = leftChild(root);
        int right = rightChild(root);
        if(index >= mid + 1) {
            set(right, mid + 1, r, index, e);
        }else {
            set(left, l, mid, index, e);
        }
        tree[root] = merge.merge(tree[left], tree[right]);
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                res.append(tree[i]);
            } else {
                res.append("null");
            }
            if (i != tree.length - 1) {
                res.append(", ");
            }
        }
        res.append("]");
        return res.toString();
    }
}
