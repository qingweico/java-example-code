package algorithm.tree;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 抽象树基类
 * 提供通用的BST(Binary Search Tree 二叉搜索树)操作模板方法
 *
 * @author zqw
 * @date 2022/3/11
 */
public abstract class AbstractTree<K extends Comparable<K>, V> {

    /**
     * 基础BST节点类
     * 包含所有BST节点共有的字段：key, value, left, right
     * 子类可以继承此类并添加额外字段(比如平衡二叉树特有的 height 字段, 红黑树特有的 color 字段)
     * 使用自引用泛型,使得 left 和 right 可以是子类类型
     *
     * @param <N> Node类型
     * @param <K> Key类型
     * @param <V> Value类型
     */
    protected static class BaseNode<N extends BaseNode<N, K, V>, K, V> {
        public K key;
        public V value;
        public N left;
        public N right;

        public BaseNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * 通用的getNode方法
     * 所有BST类型的getNode逻辑完全相同
     * 左子树上所有节点的值 < 根节点的值
     * 右子树上所有节点的值 > 根节点的值
     * 左右子树也分别是 BST
     *
     * @param node     当前节点
     * @param key      要查找的key
     * @param getKey   获取key的函数
     * @param getLeft  获取左子节点的函数
     * @param getRight 获取右子节点的函数
     * @param <N>      Node类型
     * @param <K>      Key类型
     * @return 找到的节点, 如果不存在返回null
     */
    protected static <N, K extends Comparable<K>> N getNode(
            N node, K key,
            Function<N, K> getKey,
            Function<N, N> getLeft,
            Function<N, N> getRight) {
        if (node == null) {
            return null;
        }
        K nodeKey = getKey.apply(node);
        int cmp = key.compareTo(nodeKey);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return getNode(getLeft.apply(node), key, getKey, getLeft, getRight);
        } else {
            return getNode(getRight.apply(node), key, getKey, getLeft, getRight);
        }
    }

    /**
     * BST节点处理器
     *
     * @param <N> Node类型
     * @param <K> Key类型
     * @param <V> Value类型
     */
    protected interface BstNodeHandler<N, K extends Comparable<K>, V> {
        /**
         * 获取节点的key
         */
        K getKey(N node);

        /**
         * 获取左子节点
         */
        N getLeft(N node);

        /**
         * 获取右子节点
         */
        N getRight(N node);

        /**
         * 设置左子节点
         */
        void setLeft(N node, N left);

        /**
         * 设置右子节点
         */
        void setRight(N node, N right);

        /**
         * 设置节点的value
         */
        void setValue(N node, V value);

        /**
         * 创建新节点
         */
        N createNode(K key, V value);
    }

    /**
     * BST节点插入后处理器接口
     * 用于在BST插入后执行特定操作
     *
     * @param <N> Node类型
     */
    @FunctionalInterface
    protected interface BstInsertPostProcessor<N> {
        /**
         * 处理插入后的节点
         *
         * @param node 插入后的节点
         * @return 处理后的节点
         */
        N postProcessAfterInsert(N node);
    }

    /**
     * 通用的BST插入模板方法
     * 提取了所有BST类型的公共插入逻辑
     * 1. 如果节点为null,创建新节点
     * 2. 根据key比较结果,递归插入左子树或右子树
     * 3. 如果key已存在,更新value
     * 4. 执行后处理
     *
     * @param node          当前节点
     * @param key           要插入的key
     * @param value         要插入的value
     * @param handler       BST节点处理器
     * @param postProcessor 后处理器,用于执行平衡调整等操作(可以为null)
     * @param <N>           Node类型
     * @param <K>           Key类型
     * @param <V>           Value类型
     * @return 插入后的根节点
     */
    protected static <N, K extends Comparable<K>, V> N addBst(
            N node, K key, V value,
            BstNodeHandler<N, K, V> handler,
            BstInsertPostProcessor<N> postProcessor) {

        if (node == null) {
            return handler.createNode(key, value);
        }

        K nodeKey = handler.getKey(node);
        int cmp = key.compareTo(nodeKey);
        if (cmp < 0) {
            N left = addBst(handler.getLeft(node), key, value, handler, postProcessor);
            handler.setLeft(node, left);
        } else if (cmp > 0) {
            N right = addBst(handler.getRight(node), key, value, handler, postProcessor);
            handler.setRight(node, right);
        } else {
            handler.setValue(node, value);
        }

        return postProcessor != null ? postProcessor.postProcessAfterInsert(node) : node;
    }

    /**
     * BST节点删除后处理器接口
     * 用于在BST删除后执行特定操作
     *
     * @param <N> Node类型
     */
    @FunctionalInterface
    protected interface BstRemovePostProcessor<N> {
        /**
         * 处理删除后的节点
         *
         * @param node 删除后的节点
         * @return 处理后的节点
         */
        N postProcessAfterRemove(N node);
    }

    /**
     * 通用的BST删除模板方法
     * 提取了所有BST类型的公共删除逻辑
     * 1. 如果节点为null,返回null
     * 2. 根据key比较结果,递归删除左子树或右子树
     * 3. 如果key匹配,执行删除操作
     * - 如果左子树为空,返回右子树
     * - 如果右子树为空,返回左子树
     * - 如果左右都不为空,用右子树的最小节点(后继)替换当前节点
     * 4. 执行后处理
     *
     * @param node          当前节点
     * @param key           要删除的key
     * @param handler       BST节点处理器
     * @param postProcessor 后处理器,用于执行平衡调整等操作(可以为null)
     * @param onNodeRemoved 节点被删除时的回调,用于处理size--等逻辑(可以为null)
     * @param <N>           Node类型
     * @param <K>           Key类型
     * @return 删除后的根节点
     */
    protected static <N, K extends Comparable<K>> N removeBst(
            N node, K key,
            BstNodeHandler<N, K, ?> handler,
            BstRemovePostProcessor<N> postProcessor,
            Consumer<N> onNodeRemoved) {
        if (node == null) {
            return null;
        }

        K nodeKey = handler.getKey(node);
        int cmp = key.compareTo(nodeKey);
        N cur;
        if (cmp < 0) {
            // 在左子树中删除
            N left = removeBst(handler.getLeft(node), key, handler, postProcessor, onNodeRemoved);
            handler.setLeft(node, left);
            cur = node;
        } else if (cmp > 0) {
            // 在右子树中删除
            N right = removeBst(handler.getRight(node), key, handler, postProcessor, onNodeRemoved);
            handler.setRight(node, right);
            cur = node;
        } else {
            // k == node.key

            //     x
            //    / \
            //  null  y
            if (handler.getLeft(node) == null) {
                // 左子树为空,返回右子树
                N rightNode = handler.getRight(node);
                if (onNodeRemoved != null) {
                    onNodeRemoved.accept(node);
                }
                cur = rightNode;
            }

            //     x
            //    / \
            //   y  null
            else if (handler.getRight(node) == null) {
                // 右子树为空,返回左子树
                N leftNode = handler.getLeft(node);
                if (onNodeRemoved != null) {
                    onNodeRemoved.accept(node);
                }
                cur = leftNode;
            } else {
                // 左右子树都不为空,用后继节点替换
                //              node                successor
                //              / \                  /     \
                //             x   y      ==>       x       y
                //                / \                      / \
                //        successor   o                  null o
                N successor = minBst(handler.getRight(node), handler);
                handler.setRight(successor, deleteMinBst(handler.getRight(node), handler, onNodeRemoved));
                handler.setLeft(successor, handler.getLeft(node));
                if (onNodeRemoved != null) {
                    onNodeRemoved.accept(node);
                }
                cur = successor;
            }
        }
        if (cur == null) {
            return null;
        }
        return postProcessor != null ? postProcessor.postProcessAfterRemove(cur) : cur;
    }

    /**
     * 查找BST中的最小节点
     *
     * @param node    当前节点
     * @param handler BST节点处理器
     * @param <N>     Node类型
     * @return 最小节点
     */
    protected static <N> N minBst(N node, BstNodeHandler<N, ?, ?> handler) {
        if (handler.getLeft(node) == null) {
            return node;
        }
        return minBst(handler.getLeft(node), handler);
    }

    /**
     * 删除BST中的最小节点
     *
     * @param node          当前节点
     * @param handler       BST节点处理器
     * @param onNodeRemoved 节点被删除时的回调(可以为null)
     * @param <N>           Node类型
     * @return 删除后的根节点
     */
    protected static <N> N deleteMinBst(
            N node,
            BstNodeHandler<N, ?, ?> handler,
            Consumer<N> onNodeRemoved) {
        if (handler.getLeft(node) == null) {
            N rightNode = handler.getRight(node);
            if (onNodeRemoved != null) {
                onNodeRemoved.accept(node);
            }
            return rightNode;
        }
        handler.setLeft(node, deleteMinBst(handler.getLeft(node), handler, onNodeRemoved));
        return node;
    }

    /**
     * 创建标准BST节点处理器
     * 适用于所有具有标准 {@link BaseNode} 结构(key, left, right, value)的BST实现
     *
     * @param getKey     获取key的函数
     * @param getLeft    获取左子节点的函数
     * @param getRight   获取右子节点的函数
     * @param setLeft    设置左子节点的函数
     * @param setRight   设置右子节点的函数
     * @param setValue   设置value的函数
     * @param createNode 创建新节点的函数
     * @param <N>        Node类型
     * @param <K>        Key类型
     * @param <V>        Value类型
     * @return BstNodeHandler实例
     */
    protected static <N, K extends Comparable<K>, V> BstNodeHandler<N, K, V> createBstHandler(
            Function<N, K> getKey,
            Function<N, N> getLeft,
            Function<N, N> getRight,
            java.util.function.BiConsumer<N, N> setLeft,
            java.util.function.BiConsumer<N, N> setRight,
            java.util.function.BiConsumer<N, V> setValue,
            java.util.function.BiFunction<K, V, N> createNode) {
        return new BstNodeHandler<>() {
            @Override
            public K getKey(N node) {
                return getKey.apply(node);
            }

            @Override
            public N getLeft(N node) {
                return getLeft.apply(node);
            }

            @Override
            public N getRight(N node) {
                return getRight.apply(node);
            }

            @Override
            public void setLeft(N node, N left) {
                setLeft.accept(node, left);
            }

            @Override
            public void setRight(N node, N right) {
                setRight.accept(node, right);
            }

            @Override
            public void setValue(N node, V value) {
                setValue.accept(node, value);
            }

            @Override
            public N createNode(K key, V value) {
                return createNode.apply(key, value);
            }
        };
    }

    /**
     * 创建标准BST节点(适用于继承BaseNode的节点)
     * 子类需提供节点创建工厂函数
     *
     * @param createNode 创建新节点的函数,通常需要处理size++等逻辑
     * @param <N>        Node类型(必须是BaseNode的子类)
     * @param <K>        Key类型
     * @param <V>        Value类型
     * @return BstNodeHandler实例
     */
    protected static <N extends BaseNode<N, K, V>, K extends Comparable<K>, V> BstNodeHandler<N, K, V> createStandardBstHandler(
            java.util.function.BiFunction<K, V, N> createNode) {
        return createBstHandler(
                n -> n.key,
                n -> n.left,
                n -> n.right,
                (n, left) -> n.left = left,
                (n, right) -> n.right = right,
                (n, value) -> n.value = value,
                createNode);
    }
}
