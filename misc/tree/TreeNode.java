package misc.tree;

import java.util.List;

/**
 * @author zqw
 * @date 2023/9/19
 */
public interface TreeNode<K, T> {

    /**
     * 设置节点id
     * @param id K 树节点id类型
     */
    void setId(K id);

    /**
     * 获取节点id
     * @return K
     */
    K getId();

    /**
     * 获取节点父id
     * @return K
     */
    K getPid();

    /**
     * 设置父节点
     * @param pid 父节点id
     */
    void setPid(K pid);

    /**
     * 获取孩子
     * @return List<T>
     */
    List<T> getChildren();

    /**
     * 设置children
     * @param children List<T>
     */
    void setChildren(List<T> children);

    /**
     * 添加子节点
     * @param node T
     */
    void addChild(T node);
}
