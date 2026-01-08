package collection.list;

/**
 * 跳跃表节点
 * @author zqw
 * @date 2023/12/9
 */
public class SkipListNode<E>  {

    /**
     * 成员对象
     */
    E obj;
    /**
     * 分值
     */
    double score;

    /**
     * 后退指针
     */
    SkipListNode<E> backward;

    SkipListLevel<E>[] level;

    /**
     * 层
     */
    static class SkipListLevel<E> {
        /**
         * 前进指针
         */
        SkipListNode<E> forward;

        /**
         * 跨度
         */
        long span;
    }

    @SuppressWarnings("unchecked")
    public SkipListNode(int level, double score, E obj) {
        /*创建一个层数为 level 的跳跃表节点, 并将节点的成员对象设置为 obj, 分值设置为 score*/
        this.level = new SkipListLevel[level];
        for (int i = 0; i < level; i++) {
            this.level[i] = new SkipListLevel<>();
            this.level[i].forward = null;
            this.level[i].span = 0;
        }
        this.obj = obj;
        this.score = score;
        this.backward = null;
    }

    @Override
    public String toString() {
        int levelCount = level == null ? 0 : level.length;
        StringBuilder spans = new StringBuilder("[");
        for (int i = 0; i < levelCount; i++) {
            spans.append(level[i].span);
            if (i < levelCount - 1) {
                spans.append(", ");
            }
        }
        spans.append("]");
        return "SkipListNode{" +
                "obj=" + obj +
                ", score=" + score +
                ", levelCount=" + levelCount +
                ", levelSpans=" + spans +
                ", backward=" + (backward == null ? "null" : backward.obj) +
                '}';
    }
}
