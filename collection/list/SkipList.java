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
        this.header = new SkipListNode(SKIP_LIST_MAX_LEVEL, null);
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

}

class SkipListNode {

    /**
     * 成员对象
     */
    Object obj;

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

    public SkipListNode(int level, Object obj) {
        /*创建一个层数为 level 的跳跃表节点*/
        this.level = new SkipListLevel[level];
        this.obj = obj;
    }

}
