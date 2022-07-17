package util.constants;

import org.apache.commons.lang3.SystemUtils;

/**
 * Separator Constants
 * <p/>
 * Some separators can be referenced from {@link SystemUtils}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see SystemUtils#FILE_SEPARATOR
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
