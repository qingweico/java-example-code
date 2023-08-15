package util.io;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import util.net.UrlUtils;

import java.io.File;

/**
 * {@link File} Utility
 *
 * @author zqw
 */
public class FileUtils {
    private FileUtils(){}

    /**
     * Resolve Relative Path
     *
     * @param parentDirectory Parent Directory
     * @param targetFile      Target File
     * @return If <code>targetFile</code> is a sub-file of <code>parentDirectory</code> , resolve relative path, or
     * <code>null</code>
     * @since 1.0.0
     */
    public static String resolveRelativePath(File parentDirectory, File targetFile) {
        String parentDirectoryPath = parentDirectory.getAbsolutePath();
        String targetFilePath = targetFile.getAbsolutePath();
        if (!targetFilePath.contains(parentDirectoryPath)) {
            return null;
        }
        return UrlUtils.resolvePath(StringUtils.replace(targetFilePath, parentDirectoryPath, File.pathSeparator));
    }
}
