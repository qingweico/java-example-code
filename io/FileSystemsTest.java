package io;


import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
}
