package design.structural.composite;

/**
 * 组合模式允许你将对象组合成树形结构, 并且能像处理独立对象一样处理组合对象
 *
 * @author zqw
 * @date 2026/1/19
 */
class Starter {
    public static void main(String[] args) {
        System.out.println("组合模式");


        FileNode file1 = new FileNode("a.txt", 100);
        FileNode file2 = new FileNode("b.txt", 200);

        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode subDir = new DirectoryNode("images");

        FileNode img1 = new FileNode("1.png", 500);
        FileNode img2 = new FileNode("2.png", 800);

        subDir.add(img1);
        subDir.add(img2);

        root.add(file1);
        root.add(file2);
        root.add(subDir);

        root.print("");
        System.out.println("总大小：" + root.getSize() + "KB");
    }
}
