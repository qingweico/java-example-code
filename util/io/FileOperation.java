package util.io;

import io.FileList;
import lombok.extern.slf4j.Slf4j;
import util.constants.Constants;
import util.constants.Symbol;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zqw
 * @date 2021/10/31
 */
@Slf4j
public class FileOperation {

    /**
     * Read file and put in the ArrayList
     *
     * @param filename {@code String} filename
     * @param list     {@code ArrayList<String>>}
     */
    public static void readFileToArrayList(String filename, ArrayList<String> list) {

        if (filename == null) {
            log.error("file is null");
            return;
        }
        if (list == null) {
            log.error("list is null");
            return;
        }
        Scanner scanner;
        try {
            File file = new File(filename);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), StandardCharsets.UTF_8);
                scanner.useLocale(Locale.ENGLISH);
            } else {
                log.error("file {} is not exist", filename);
                return;
            }

        } catch (IOException ioe) {
            log.error("Cannot open {}", filename);
            return;
        }

        if (scanner.hasNextLine()) {
            String contents = scanner.useDelimiter("\\A").next();
            int start = firstCharacterIndex(contents, 0);
            for (int i = start + 1; i <= contents.length(); ) {
                if (i == contents.length() || !Character.isLetter(contents.charAt(i))) {
                    String word = contents.substring(start, i).toLowerCase();
                    list.add(word);
                    start = firstCharacterIndex(contents, i);
                    i = start + 1;
                } else {
                    i++;
                }
            }
        }
    }

    /**
     * 返回字符串中第一个字母的索引
     *
     * @param s     字符串
     * @param start 起始位置
     * @return "13llo, 0" => 2
     */
    private static int firstCharacterIndex(String s, int start) {

        for (int i = start; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {
                return i;
            }
        }
        return s.length();
    }


    public static void copyFileByStream(File source, File target) {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(target)) {
            byte[] buffer = new byte[Constants.KB];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("io error");
        }
    }

    public static void copyFileByChannel(File source, File target) {
        try (FileInputStream fis = new FileInputStream(source); FileOutputStream fos = new FileOutputStream(target)) {
            FileChannel sourceChannel = fis.getChannel();
            FileChannel targetChannel = fos.getChannel();
            for (long count = sourceChannel.size(); count > 0; ) {
                long transferred = sourceChannel.transferTo(sourceChannel.position(), count, targetChannel);
                sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }
        } catch (IOException e) {
            log.error("io error");
        }
    }

    /**
     * NIO 和操作系统底层密切相关, 每个平台都有自己实现的文件系统逻辑
     * <p>
     * {@link FileSystemProvider}
     * {@code Windows} {@see sun.nio.fs.WindowsFileSystemProvider}
     * {@code Linux Mac} {@see UnixFileSystemProvider -> UnixCopyFileSystem#transfer() ->  UnixCopyFile.c}
     *
     * @param source {@link  Path}
     * @param target {@link Path}
     */
    public static void fileCopy(Path source, Path target) {
        try {
            Files.copy(source, target);
        } catch (IOException e) {
            log.error("io error");
        }
    }

    /**
     * 使用 Files 工具类快速读取文件
     *
     * @param fileName [路径]文件名称
     * @return 文本字符流
     * 快速写入文件{@link Files#writeString(Path, CharSequence, OpenOption...)}
     */
    public static String fastReadFile(String fileName, boolean isClassPath) {
        String result;
        // 读取ClassPath路径
        if (isClassPath) {
            URL url = FileOperation.class.getClassLoader().getResource(fileName);
            if (url == null) {
                log.error("[classpath] : {} not found!", fileName);
                return Symbol.EMPTY;
            }
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));
                // TODO: 无论使用readString or readAllBytes 读取字节码文件时都会乱码, 文本文件则正常
                result = new String(bytes, 0, bytes.length);
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }

        } else {
            // 读取项目根目录路径
            try {
                result = Files.readString(Paths.get(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 快速遍历文件夹并打印出其中所有的文件, {@link  FileList} 使用递归
     *
     * @param directory 文件夹名称
     */
    public static void fileList(String directory) {
        try (Stream<Path> pathStream = Files.walk(Paths.get(directory))) {
            pathStream.filter(Files::isRegularFile).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按行读取文本文件,并以 {@code delimiter} 连接成一行String
     *
     * @param file      [路径]文件名称
     * @param delimiter 分割符
     * @return the collected string of a line
     */
    public static String readAsAline(String file, String delimiter) {
        String collected;
        try (Stream<String> lines = Files.lines(Paths.get(file))) {
            // joining : 在每个元素之间使用的分隔符
            // eg: 1\n2\n3 + "-" => 1-2-3; not use prefix and suffix
            collected = lines.collect(Collectors.joining(delimiter));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return collected;
    }

    public static String fastReadFile(String fileName) {
        return fastReadFile(fileName, false);
    }

    public static String readAsAline(String file) {
        return readAsAline(file, Symbol.EMPTY);
    }

    public static void filterFileByTarget(String directory, String fileSuffix, String target) {
        try {
            Files.walkFileTree(Paths.get(directory), new FileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(fileSuffix)) {
                        String context = Files.readString(file);
                        if (context.contains(target)) {
                            System.out.println(file);
                        }

                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
