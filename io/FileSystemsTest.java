package io;


import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.spi.FileSystemProvider;
import java.util.stream.Stream;

/**
 * @author zqw
 * @date 2025/8/9
 */
public class FileSystemsTest {

    @Test
    public void winFileSystem() throws IOException {
        FileSystem windowsFileSystem = FileSystems.getDefault();
        Path rootDir = windowsFileSystem.getPath(ClassUtils.getPackageName(this.getClass()));
        try (Stream<Path> stream = Files.walk(rootDir)) {
            stream.forEach(System.out::println);
        }
    }

    @Test
    public void zipFileSystem() throws IOException {
        // 通过 newFileSystem 创建的文件系统必须显式调用 close()
        // 方法来释放资源并确保所有更改被正确写回底层文件
        FileSystem zipFileSystem = FileSystems.newFileSystem(Path.of("lib/InstrumentationAgent.jar"));
        Path rootDir = zipFileSystem.getPath("/");
        try (Stream<Path> stream = Files.walk(rootDir)) {
            stream.forEach(System.out::println);
        }
        zipFileSystem.close();
    }

    @Test
    public void jrtFileSystem() throws IOException {
        FileSystem jrtFileStream = FileSystems.getFileSystem(URI.create("jrt:///"));
        Path rootDir = jrtFileStream.getPath("/");
        try (Stream<Path> stream = Files.walk(rootDir)) {
            stream.forEach(System.out::println);
        }
    }

    @Test
    public void getFileSystemByPath() {
        Path path = Paths.get(ClassUtils.getPackageName(this.getClass()));
        FileSystem fileSystem = path.getFileSystem();
        System.out.println(fileSystem.getClass().getSimpleName());
    }

    @Test
    public void getFileSystemByProvider() {
        FileSystemProvider provider = FileSystems.getDefault().provider();
        FileSystem fileSystem = provider.getFileSystem(URI.create("file:///"));
        System.out.println(fileSystem.getClass().getSimpleName());
    }
}
