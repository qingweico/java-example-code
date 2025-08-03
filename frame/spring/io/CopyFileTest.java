package frame.spring.io;

import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import cn.qingweico.constants.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author zqw
 * @date 2023/6/18
 * @see FileCopyUtils
 * @see StreamUtils
 */
public class CopyFileTest {

    File file = new File(Constants.DEFAULT_FILE_PATH_MAME);
    File toFile = new File(Constants.DEFAULT_FILE_OUTPUT_PATH_MAME);

    @Test
    public void input() throws IOException {

        // ------------- INPUT
        // To Byte Array
        byte[] bytes = FileCopyUtils.copyToByteArray(file);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));

        // To String
        String str = FileCopyUtils.copyToString(new FileReader(file));
        System.out.println(str);

        // StreamUtils#copyToString
        System.out.println(StreamUtils.copyToString(new FileInputStream(file), StandardCharsets.UTF_8));
    }

    @Test
    public void output() throws IOException {

        // ------------- OUTPUT
        FileCopyUtils.copy("--Long".getBytes(), file);
        FileCopyUtils.copy(file, toFile);
    }
}
