package misc.tree;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zqw
 * @date 2023/9/19
 */
@ToString
public class MenuTreeNode implements TreeNode<String, MenuTreeNode> {

    private String id;
    private String pid;
    private String name;
    private String sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    private List<MenuTreeNode> children = new ArrayList<>();

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPid() {
        return pid;
    }

    @Override
    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public List<MenuTreeNode> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<MenuTreeNode> children) {
        this.children = children;
    }

    @Override
    public void addChild(MenuTreeNode node) {
        children.add(node);
    }
}
