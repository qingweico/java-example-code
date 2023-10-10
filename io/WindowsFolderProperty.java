package io;

import util.Print;

import java.io.File;

/**
 * 类似 Windows 文件夹属性, 1 占用空间 2 包含的文件夹数量 3 包含的文件数量
 *
 * @author zqw
 * @date 2023/10/7
 */
public class WindowsFolderProperty {

    static long totalSize = 0L;
    static int folderNumber = 0;
    static int fileNumber = 0;

    public static void main(String[] args) {
        helperResult(new File((args[0])));
        Print.grace("占用空间(字节)", totalSize);
        Print.grace("包含的文件夹数量(个)", folderNumber);
        Print.grace("包含的文件数量(个)", fileNumber);
    }

    public static void helperResult(File target) {
        if (target.isFile()) {
            fileNumber++;
            totalSize += target.length();
            return;
        }
        File[] files = target.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                folderNumber++;
            }
            helperResult(file);
        }
    }
}
