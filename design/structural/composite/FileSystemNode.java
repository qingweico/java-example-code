package design.structural.composite;

/**
 * @author zqw
 * @date 2026/1/19
 */
abstract class FileSystemNode {

    protected String name;

    public FileSystemNode(String name) {
        this.name = name;
    }

    public abstract long getSize();

    public abstract void print(String indent);
}

