package misc.tree;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import cn.qingweico.io.Print;
import cn.qingweico.io.FileUtils;

import java.util.*;

/**
 * @author zqw
 * @date 2023/9/19
 */
public class BuildTree {
    public List<Map<String, Object>> getChildrenNode(String parentId, List<Map<String, Object>> menuNodes) {
        List<Map<String, Object>> menuTree = new ArrayList<>();
        List<Map<String, Object>> nodes = menuNodes.stream()
                .filter(e -> StringUtils.equals(e.get("pid").toString(), parentId))
                .sorted(Comparator.comparing(e -> String.valueOf(e.get("sort"))))
                .toList();
        if (!nodes.isEmpty()) {
            nodes.forEach(node -> {
                List<Map<String, Object>> childrenNode =
                        this.getChildrenNode(node.get("id").toString(), menuNodes);
                if (!CollectionUtils.isEmpty(childrenNode)) {
                    node.put("children", childrenNode);
                }
                menuTree.add(node);
            });
        }
        return menuTree;
    }

    public List<Map<String, Object>> loadTreeDataToMap() {
        String jsonStr = readFileToString();
        Map<String, List<Map<String, Object>>> map = new Gson().fromJson(jsonStr, new TypeToken<HashMap<String, List<Map<String, Object>>>>() {
        }.getType());
        return map.get("root");
    }

    public List<MenuTreeNode> loadTreeDataToEntity() {
        String jsonStr = readFileToString();
        Map<String, List<MenuTreeNode>> map = new Gson().fromJson(jsonStr, new TypeToken<HashMap<String, List<MenuTreeNode>>>() {
        }.getType());
        return map.get("root");
    }

    public String readFileToString() {
        return FileUtils.fastReadFile("misc/tree/MenuTree.json");
    }

    public static void main(String[] args) {
        BuildTree bt = new BuildTree();
        StopWatch sw = new StopWatch("Build Tree Data Comparison");
        Print.println("-------------");
        sw.start("getChildrenNode");
        Print.printColl(bt.getChildrenNode("0", bt.loadTreeDataToMap()));
        sw.stop();
        Print.println("-------------");
        sw.start("getTreesFast");
        Print.printColl(bt.getTreesFast(bt.loadTreeDataToEntity()));
        sw.stop();
        Print.println("-------------");
        sw.start("buildTreeNode");
        Print.printColl(bt.buildTreeNode(bt.loadTreeDataToEntity()));
        sw.stop();
        System.out.println(sw.prettyPrint());
    }

    public List<MenuTreeNode> getTreesFast(List<MenuTreeNode> list) {
        List<MenuTreeNode> menuTreeNodeList = list.stream().map(e -> {
            MenuTreeNode menuTreeNode = new MenuTreeNode();
            menuTreeNode.setId(e.getId());
            menuTreeNode.setPid(e.getPid());
            menuTreeNode.setName(e.getName());
            menuTreeNode.setSort(e.getSort());
            return menuTreeNode;
        }).sorted(Comparator.comparing(MenuTreeNode::getSort)).toList();
        TreeFactory<String, MenuTreeNode> treeFactory = new TreeFactory<>();
        return treeFactory.buildTree(menuTreeNodeList);
    }

    public List<MenuTreeNode> buildTreeNode(List<MenuTreeNode> list) {
        return list.stream()
                .filter(e -> StringUtils.equals(e.getPid(), "0"))
                .peek(e -> e.setChildren(getChildrenNode(e, list)))
                .toList();
    }

    private List<MenuTreeNode> getChildrenNode(MenuTreeNode root, List<MenuTreeNode> list) {
        return list.stream()
                .filter(e -> e.getPid().equals(root.getId()))
                .peek(e -> e.setChildren(getChildrenNode(e, list)))
                .toList();
    }
}
