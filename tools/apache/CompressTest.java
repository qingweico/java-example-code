package tools.apache;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.junit.Test;
import util.constants.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Apache 压缩工具
 * @author zqw
 * @date 2023/2/10
 */
public class CompressTest {

    @Test
    public void compress() {
        String willCompressFile = Constants.DEFAULT_FILE_PATH_MAME;
        String afterCompressFile = "data.zip";


        // 创建压缩对象
        ZipArchiveEntry entry = new ZipArchiveEntry(willCompressFile);
        // 要压缩的文件
        File f = new File(willCompressFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(f);
            // 输出的对象 压缩的文件
            ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(new File(afterCompressFile));
            zipOutput.putArchiveEntry(entry);
            int j;
            while ((j = fis.read()) != -1) {
                zipOutput.write(j);
            }
            zipOutput.closeArchiveEntry();
            zipOutput.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
