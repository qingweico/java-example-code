package design.structural.composite;

/**
 * @author zqw
 * @date 2026/1/19
 */
public class FileNode extends FileSystemNode {

    private long size;

    public FileNode(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "- 文件：" + name + " (" + size + "KB)");
    }
}
