package util.io;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import util.net.URLUtils;

import java.io.File;

/**
 * {@link File} Utility
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 */
public abstract class FileUtils {

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
        return URLUtils.resolvePath(StringUtils.replace(targetFilePath, parentDirectoryPath, SystemUtils.FILE_SEPARATOR));
    }
}
