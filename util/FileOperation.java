package util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

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

    private static int firstCharacterIndex(String s, int start) {

        for (int i = start; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {
                return i;
            }
        }
        return s.length();
    }

    public static void copyFileByStream(File source, File dest) {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[Constants.KB];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("io error");
        }
    }

    public static void copyFileByChannel(File source, File dest) {
        try (FileInputStream fis = new FileInputStream(source); FileOutputStream fos = new FileOutputStream(dest)) {
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
     * {@link java.nio.file.spi.FileSystemProvider}
     * {@code Windows} {@see sun.nio.fs.WindowsFileSystemProvider}
     * {@code Linux Mac} {@see UnixFileSystemProvider -> UnixCopyFileSystem#transfer() ->  UnixCopyFile.c}
     *
     * @param source {@link  Path}
     * @param dest   {@link Path}
     */
    public static void fileCopy(Path source, Path dest) {
        try {
            Files.copy(source, dest);
        } catch (IOException e) {
            log.error("io error");
        }
    }
}
