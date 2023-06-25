package util.constants;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;

/**
 * Separator Constants
 * <p/>
 * Some separators can be referenced from {@link SystemUtils}
 *
 * @author zqw
 * @see File#pathSeparator
 */
public interface SeparatorConstants {

    /**
     * Exclamation : "!"
     */
    String EXCLAMATION = "!";

    /**
     * Archive Entity Separator : "!/"
     */
    String ARCHIVE_ENTITY = EXCLAMATION + PathConstants.SLASH;

    /**
     * Query String Separator : "?"
     */
    String QUERY_STRING = "?";

}
