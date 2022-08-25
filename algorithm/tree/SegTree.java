package algorithm.tree;

/**
 * --------------------------- 线段树 ---------------------------
 * @author zqw
 * @date 2021/11/1
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

    public E query(int l, int r) {
        if (l < 0 || r < 0 || l > tree.length || r > tree.length) {
            throw new IllegalArgumentException();
        }
        return query(0, 0, data.length - 1, l, r);
    }

    private E query(int root, int l, int r, int lLimit, int rLimit) {
        if(l == lLimit && r == rLimit) {
            return tree[root];
        }
        int left = leftChild(root);
        int right = rightChild(root);
        int mid = l + ((r - l) >> 1);
        if(lLimit >= mid + 1) {
            return query(right, mid + 1, r, lLimit, rLimit);
        }else if(rLimit <= mid) {
            return query(left, l, mid, lLimit, rLimit);
        }
        E leftRes = query(left, l, mid, lLimit, mid);
        E rightRes = query(right, mid + 1, r, mid + 1, rLimit);
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
