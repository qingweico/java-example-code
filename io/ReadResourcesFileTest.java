package io;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;
import cn.qingweico.constants.Symbol;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * 读取 resources 目录下文件的方式
 *
 * @author zqw
 * @date 2022/8/11
 */
@Slf4j
public class ReadResourcesFileTest {
    String fileName = Constants.DEFAULT_FILE_PATH_MAME;

    public static void getFileContent(Object fileInPath) {
        BufferedReader br;
        if (fileInPath == null) {
            return;
        }
        try {
            if (fileInPath instanceof String) {
                br = new BufferedReader(new FileReader((String) fileInPath));
            } else if (fileInPath instanceof InputStream) {
                br = new BufferedReader(new InputStreamReader((InputStream) fileInPath));
            } else {
                log.error("fileInput type error!");
                return;
            }
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            log.error("io error!: {}", e.getMessage());
        }
    }

    @Test
    public void m1() {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();
        log.info("path: {}", path);
        String filePath = path + fileName;
        log.info("filePath: {}", filePath);
        getFileContent(filePath);
    }

    @Test
    public void m2() {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).getPath();
        log.info("path: {}", path);
        // 路径中带有中文会被编码
        String filePath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        log.info("filePath: {}", filePath);
        getFileContent(filePath);
    }

    @Test
    public void m3() throws MalformedURLException {
        URL url = new URL("https://a.b.c?k=v");
        // 如果是文件路径的话,两者效果是一样的; 若是url路径 getFile()则是带有参数的路径
        System.out.println(url.getPath());
        System.out.println(url.getFile());
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).getFile();
        log.info("path: {}", path);
        String filePath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        log.info("filePath: {}", filePath);
        getFileContent(filePath);
    }

    @Test
    public void m4() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        getFileContent(inputStream);
    }

    @Test
    public void m5() {
        // 不使用getClassLoader,使用getResourceAsStream直接从resources路径下获取
        InputStream inputStream = this.getClass().getResourceAsStream("/" + fileName);
        getFileContent(inputStream);
    }

    @Test
    public void m6() throws IOException {
        /*使用 Spring 中 ClassPathResource类*/
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        InputStream inputStream = classPathResource.getInputStream();
        getFileContent(inputStream);
    }

    @Test
    public void m7() {
        /*通过绝对路径获取*/
        String rootPath = System.getProperty("user.dir");
        log.info("rootPath: {}", rootPath);
        String filePath = rootPath + Symbol.BACKSLASH + fileName;
        getFileContent(filePath);
    }

    @Test
    public void m8() throws IOException {
        /*还是使用绝对路径*/
        File dict = new File("");
        // getCanonicalPath 相对于 getAbsolutePath 会把路径中../; ./符号去掉; 都会返回绝对路径
        System.out.println(dict.getAbsolutePath());
        String canonicalPath = dict.getCanonicalPath();
        System.out.println(canonicalPath);
        String filePath = canonicalPath +  Symbol.BACKSLASH + fileName;
        getFileContent(filePath);
    }
    @Test
    public void m9(){
        /*设置环境变量*/
        Map<String, String> envMap = System.getenv();
        Print.printMap(envMap);
        File dict = new File("");
        String key = "env_path";
        String value = dict.getAbsolutePath();
        log.info("currPath: {}", value);
        System.setProperty(key, value);
        String rootPath = System.getProperty(key);
        log.info("rootPath: {}", rootPath);
        String filePath = rootPath +  Symbol.BACKSLASH + fileName;
        getFileContent(filePath);
    }
}
