package collection.list;

/**
 * 参考 redis src/t_zset.c 实现跳跃表
 *
 * @author zqw
 * @date 2023/12/9
 */
public class SkipList {

    private static final int SKIP_LIST_MAX_LEVEL = 32;

    private static final double SKIP_LIST_P = 0.25;

    private static final int UPPER_LIMIT = 0xFFFF;

    /**
     * 表头节点和表尾节点
     **/
    SkipListNode header, tail;
    /**
     * 表中节点的数量
     */
    long length;
    /**
     * 表中层数最大的节点的层数
     **/
    int level;

    public SkipList() {
        int j;
        /*设置高度和起始层数*/
        this.level = 1;
        this.length = 0;

        /*初始化表头节点*/
        this.header = new SkipListNode(SKIP_LIST_MAX_LEVEL, 0, null);
        for (j = 0; j < SKIP_LIST_MAX_LEVEL; j++) {
            this.header.level[j].forward = null;
            this.header.level[j].span = 0;
        }
        this.header.backward = null;
        this.tail = null;
    }

    /**
     * 返回一个随机值, 用作新跳跃表节点的层数
     * 返回值介乎 1 和 SKIP_LIST_MAX_LEVEL 之间(包含 SKIP_LIST_MAX_LEVEL)
     * 根据随机算法所使用的幂次定律, 越大的值生成的几率越小
     *
     * @return 跳跃表节点的层数
     */
    int slRandomLevel() {
        int level = 1;

        while ((Math.random() * UPPER_LIMIT) < (SKIP_LIST_P * UPPER_LIMIT)) {
            level += 1;
        }
        return Math.min(level, SKIP_LIST_MAX_LEVEL);
    }

    private void insert(SkipList sl, double score, Comparable<Object> obj) {
        SkipListNode[] update = new SkipListNode[SKIP_LIST_MAX_LEVEL];
        SkipListNode x;
        int[] rank = new int[SKIP_LIST_MAX_LEVEL];
        int i, level;
        // 在各个层查找节点的插入位置
        // T_worst = O(N^2), T_avg = O(N log N)
        x = sl.header;
        for (i = sl.level - 1; i >= 0; i--) {
            rank[i] = i == (i = sl.level - 1) ? 0 : rank[i + 1];
            while (x.level[i].forward != null && (x.level[i].forward.score < score)
                    // 比对分值
                    || (x.level[i].forward.score == score &&
                    // 比对成员
                    x.level[i].forward.obj.compareTo(obj) < 0)) {
                // 记录沿途跨越了多少个节点
                rank[i] += x.level[i].span;

                // 移动至下一指针
                x = x.level[i].forward;
            }
            // 记录将要和新节点相连接的节点
            update[i] = x;
        }
        level = slRandomLevel();

        // 如果新节点的层数比表中其他节点的层数都要大
        // 那么初始化表头节点中未使用的层, 并将它们记录到 update 数组中
        // 将来也指向新节点
        if (level > sl.level) {
            // 初始化未使用层
            for (i = sl.level; i < level; i++) {
                rank[i] = 0;
                update[i] = header;
                update[i].level[i].span = sl.level;
            }
            sl.level = level;
        }

        // 创建新节点
        x = new SkipListNode(level, score, obj);


        // 将前面记录的指针指向新节点, 并做相应的设置
        for (i = 0; i < level; i++) {

            // 设置新节点的 forward 指针
            x.level[i].forward = update[i].level[i].forward;

            // 将沿途记录的各个节点的 forward 指针指向新节点
            update[i].level[i].forward = x;

            // 计算新节点跨越的节点数量
            x.level[i].span = update[i].level[i].span - (rank[0] - rank[i]);

            // 更新新节点插入之后，沿途节点的 span 值
            // 其中的 +1 计算的是新节点
            update[i].level[i].span = (rank[0] - rank[i]) + 1;
        }

        // 未接触的节点的 span 值也需要增一, 这些节点直接从表头指向新节点
        for (i = level; i < sl.level; i++) {
            update[i].level[i].span++;
        }

        // 设置新节点的后退指针
        x.backward = (update[0] == sl.header) ? null : update[0];
        if (x.level[0].forward != null) {
            x.level[0].forward.backward = x;

        } else {
            sl.tail = x;
        }
        // 跳跃表的节点计数增一
        sl.length++;
    }

}

class SkipListNode {

    /**
     * 成员对象
     */
    Comparable<Object> obj;
    /**
     * 分值
     */
    double score;

    /**
     * 后退指针
     */
    SkipListNode backward;

    SkipListLevel[] level;

    /**
     * 层
     */
    static class SkipListLevel {
        /**
         * 前进指针
         */
        SkipListNode forward;

        /**
         * 跨度
         */
        int span;
    }

    public SkipListNode(int level, double score, Comparable<Object> obj) {
        /*创建一个层数为 level 的跳跃表节点, 并将节点的成员对象设置为 obj, 分值设置为 score*/
        this.level = new SkipListLevel[level];
        this.obj = obj;
        this.score = score;
    }
}
