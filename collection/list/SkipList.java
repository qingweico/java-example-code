package collection.list;

import annotation.NotThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 参考 redis src/t_zset.c 实现跳跃表
 * redis 中 Zset 实现为什么不使用 二叉树或者堆, 而使用跳表
 * 1 时间复杂度 跳表支持 O(logN) 的插入、删除和查找
 * 2 空间复杂度 仅需额外存储少量层级指针, 更节省内存
 * 3 跳表范围查询的高效性
 * 4 跳表实现更简单性
 * @author zqw
 * @date 2023/12/9
 */
@NotThreadSafe
public class SkipList<E extends Comparable<E>> {

    private static final int SKIP_LIST_MAX_LEVEL = 32;

    private static final double SKIP_LIST_P = 0.25;

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    /**
     * 表头节点和表尾节点
     **/
    SkipListNode<E> header, tail;
    /**
     * 表中节点的数量
     */
    long length;
    /**
     * 表中层数最大的节点的层数
     **/
    int level;

    public SkipList() {
        /*设置高度和起始层数*/
        this.level = 1;
        this.length = 0;

        /*初始化表头节点*/
        this.header = new SkipListNode<>(SKIP_LIST_MAX_LEVEL, 0, null);
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

        while (RANDOM.nextDouble() < SKIP_LIST_P && level < SKIP_LIST_MAX_LEVEL) {
            level++;
        }
        return level;
    }

    /**
     * 插入一个新节点到跳跃表中
     * 如果已存在相同的 score 和 obj,则先删除旧节点再插入新节点(更新操作)
     *
     * @param score 分值
     * @param obj   成员对象
     * @return 插入的节点
     */
    public SkipListNode<E> insert(double score, E obj) {
        @SuppressWarnings("unchecked")
        SkipListNode<E>[] update = new SkipListNode[SKIP_LIST_MAX_LEVEL];
        SkipListNode<E> x;
        long[] rank = new long[SKIP_LIST_MAX_LEVEL];
        int i, level;

        /*
         * 在各个层查找节点的插入位置
         * T_worst = O(N^2), T_avg = O(N log N)
         */
        x = this.header;
        for (i = this.level - 1; i >= 0; i--) {
            /*
             * 初始化 rank[i] 为前一层级的 rank
             */
            rank[i] = (i == (this.level - 1)) ? 0 : rank[i + 1];
            /*
             * 在当前层级向前查找,直到找到合适的插入位置
             */
            while (x.level[i].forward != null
                    && (x.level[i].forward.score < score
                    || (x.level[i].forward.score == score
                    && x.level[i].forward.obj.compareTo(obj) < 0))) {
                /*
                 * 记录沿途跨越了多少个节点
                 */
                rank[i] += x.level[i].span;
                /*
                 * 移动至下一指针
                 */
                x = x.level[i].forward;
            }
            /*
             * 记录将要和新节点相连接的节点
             */
            update[i] = x;
        }

        /*
         * 检查是否已存在相同的 score 和 obj
         * 如果存在,直接返回现有节点(更新操作)
         * 注意：x 现在指向的是插入位置的前驱节点
         */
        SkipListNode<E> next = x.level[0].forward;
        if (next != null
                && next.score == score
                && next.obj.compareTo(obj) == 0) {
            /*
             * 找到相同的节点,直接返回现有节点
             * 因为 score 和 obj 都相同,不需要删除和重新插入
             */
            return next;
        }

        /*
         * 随机生成新节点的层数
         */
        level = slRandomLevel();

        /*
         * 如果新节点的层数比表中其他节点的层数都要大
         * 那么初始化表头节点中未使用的层,并将它们记录到 update 数组中
         * 对于新层,span值应该设置为从header到新插入位置的距离,即rank[0]
         */
        if (level > this.level) {
            for (i = this.level; i < level; i++) {
                rank[i] = 0;
                update[i] = this.header;
                update[i].level[i].span = rank[0];
            }
            this.level = level;
        }

        /*
         * 创建新节点
         */
        x = new SkipListNode<>(level, score, obj);

        /*
         * 将前面记录的指针指向新节点,并做相应的设置
         */
        for (i = 0; i < level; i++) {
            /*
             * 设置新节点的 forward 指针
             */
            x.level[i].forward = update[i].level[i].forward;

            /*
             * 将沿途记录的各个节点的 forward 指针指向新节点
             */
            update[i].level[i].forward = x;

            /*
             * 计算新节点跨越的节点数量
             */
            x.level[i].span = update[i].level[i].span - (rank[0] - rank[i]);

            /*
             * 更新新节点插入之后,沿途节点的 span 值
             * 其中的 +1 计算的是新节点
             */
            update[i].level[i].span = (rank[0] - rank[i]) + 1;
        }

        /*
         * 未接触的节点的 span 值也需要增一,这些节点直接从表头指向新节点
         */
        for (i = level; i < this.level; i++) {
            update[i].level[i].span++;
        }

        /*
         * 设置新节点的后退指针
         */
        x.backward = (update[0] == this.header) ? null : update[0];
        if (x.level[0].forward != null) {
            x.level[0].forward.backward = x;
        } else {
            this.tail = x;
        }
        /*
         * 跳跃表的节点计数增一
         */
        this.length++;
        return x;
    }

    /**
     * 删除节点
     * 参考 Redis zslDelete 实现
     *
     * @param score 分值
     * @param obj   成员对象
     * @return 被删除的节点,如果不存在返回null
     */
    @SuppressWarnings("unchecked")
    public SkipListNode<E> delete(double score, E obj) {
        SkipListNode<E>[] update = new SkipListNode[SKIP_LIST_MAX_LEVEL];
        SkipListNode<E> x;
        int i;

        /*
         * 查找要删除的节点
         */
        x = this.header;
        for (i = this.level - 1; i >= 0; i--) {
            while (x.level[i].forward != null
                    && (x.level[i].forward.score < score
                    || (x.level[i].forward.score == score
                    && x.level[i].forward.obj.compareTo(obj) < 0))) {
                x = x.level[i].forward;
            }
            update[i] = x;
        }

        /*
         * x 可能是要删除的节点,也可能是它的前驱
         */
        x = x.level[0].forward;
        if (x != null && x.score == score && x.obj.compareTo(obj) == 0) {
            /*
             * 删除节点
             */
            deleteNode(x, update);
            return x;
        }
        return null;
    }

    /**
     * 内部方法：删除节点并更新相关指针
     */
    private void deleteNode(SkipListNode<E> x, SkipListNode<E>[] update) {
        int i;
        /*
         * 更新所有指向被删除节点的指针
         */
        for (i = 0; i < this.level; i++) {
            if (update[i].level[i].forward == x) {
                update[i].level[i].span += x.level[i].span - 1;
                update[i].level[i].forward = x.level[i].forward;
            } else {
                update[i].level[i].span -= 1;
            }
        }

        /*
         * 更新后退指针
         */
        if (x.level[0].forward != null) {
            x.level[0].forward.backward = x.backward;
        } else {
            this.tail = x.backward;
        }

        /*
         * 更新跳跃表的层数
         */
        while (this.level > 1 && this.header.level[this.level - 1].forward == null) {
            this.level--;
        }

        /*
         * 更新节点计数
         */
        this.length--;
    }

    /**
     * 查找节点
     * 参考 Redis zslGetElementByRank 实现
     *
     * @param score 分值
     * @param obj   成员对象
     * @return 找到的节点,如果不存在返回null
     */
    public SkipListNode<E> search(double score, E obj) {
        SkipListNode<E> x = this.header;
        int i;

        /*
         * 从最高层开始向下查找
         */
        for (i = this.level - 1; i >= 0; i--) {
            while (x.level[i].forward != null
                    && (x.level[i].forward.score < score
                    || (x.level[i].forward.score == score
                    && x.level[i].forward.obj.compareTo(obj) < 0))) {
                x = x.level[i].forward;
            }
        }

        /*
         * x 可能是目标节点,也可能是它的前驱
         */
        x = x.level[0].forward;
        if (x != null && x.score == score && x.obj.compareTo(obj) == 0) {
            return x;
        }
        return null;
    }

    /**
     * 获取节点的排名(rank)
     * 排名从1开始,rank=1表示第一个节点
     * 参考 Redis zslGetRank 实现
     *
     * @param score 分值
     * @param obj   成员对象
     * @return 排名,如果不存在返回0
     */
    public long getRank(double score, E obj) {
        long rank = 0;
        SkipListNode<E> x = this.header;
        int i;

        /*
         * 从最高层开始向下查找
         */
        for (i = this.level - 1; i >= 0; i--) {
            while (x.level[i].forward != null
                    && (x.level[i].forward.score < score
                    || (x.level[i].forward.score == score
                    && x.level[i].forward.obj.compareTo(obj) < 0))) {
                rank += x.level[i].span;
                x = x.level[i].forward;
            }
        }

        /*
         * x 可能是目标节点,也可能是它的前驱
         */
        x = x.level[0].forward;
        if (x != null && x.score == score && x.obj.compareTo(obj) == 0) {
            return rank + 1;
        }
        return 0;
    }

    /**
     * 根据排名获取节点
     * 排名从1开始,rank=1表示第一个节点
     * 参考 Redis zslGetElementByRank 实现
     *
     * @param rank 排名
     * @return 节点,如果排名超出范围返回null
     */
    public SkipListNode<E> getElementByRank(long rank) {
        SkipListNode<E> x = this.header;
        long traversed = 0;
        int i;

        /*
         * 从最高层开始向下查找
         */
        for (i = this.level - 1; i >= 0; i--) {
            while (x.level[i].forward != null
                    && (traversed + x.level[i].span) <= rank) {
                traversed += x.level[i].span;
                x = x.level[i].forward;
            }
            if (traversed == rank) {
                return x;
            }
        }
        return null;
    }

    /**
     * 获取范围内的节点
     * 参考 Redis zslRange 实现
     *
     * @param start 起始排名(包含)
     * @param end   结束排名(包含)
     * @return 范围内的节点数组
     */
    @SuppressWarnings("unchecked")
    public SkipListNode<E>[] range(long start, long end) {
        if (start < 1) {
            start = 1;
        }
        if (end > this.length) {
            end = this.length;
        }
        if (start > end) {
            return new SkipListNode[0];
        }

        int len = (int) (end - start + 1);
        SkipListNode<E>[] result = new SkipListNode[len];

        /*
         * 找到起始节点
         */
        SkipListNode<E> node = getElementByRank(start);
        for (int i = 0; i < len && node != null; i++) {
            result[i] = node;
            node = node.level[0].forward;
        }

        return result;
    }

    /**
     * 根据分值范围获取节点
     *
     * @param min 最小分值(包含)
     * @param max 最大分值(包含)
     * @return score 在 [min, max] 范围内的节点列表
     */
    public List<SkipListNode<E>> rangeByScore(double min, double max) {
        List<SkipListNode<E>> result = new ArrayList<>();
        SkipListNode<E> x;
        int i;

        /*
         * 从最高层开始向下查找第一个 score >= min 的节点
         */
        x = this.header;
        for (i = this.level - 1; i >= 0; i--) {
            while (x.level[i].forward != null
                    && x.level[i].forward.score < min) {
                x = x.level[i].forward;
            }
        }

        /*
         * x 现在指向的是第一个 score >= min 的节点的前驱
         * 移动到第一个符合条件的节点
         */
        x = x.level[0].forward;

        /*
         * 遍历所有 score <= max 的节点
         */
        while (x != null && x.score <= max) {
            result.add(x);
            x = x.level[0].forward;
        }

        return result;
    }

    /**
     * 获取跳跃表的长度
     */
    public long length() {
        return this.length;
    }

    /**
     * 判断跳跃表是否为空
     */
    public boolean isEmpty() {
        return this.length == 0;
    }
}
