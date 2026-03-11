package design.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zqw
 * @date 2026/1/19
 */
class DirectoryNode extends FileSystemNode {

    private final List<FileSystemNode> children = new ArrayList<>();

    public DirectoryNode(String name) {
        super(name);
    }

    public void add(FileSystemNode node) {
        children.add(node);
    }

    public void remove(FileSystemNode node) {
        children.remove(node);
    }

    @Override
    public long getSize() {
        long total = 0;
        for (FileSystemNode node : children) {
            total += node.getSize();
        }
        return total;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "+ 文件夹：" + name);
        for (FileSystemNode node : children) {
            node.print(indent + "  ");
        }
    }
}
