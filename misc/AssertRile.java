package misc;

import junit.framework.Assert;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zqw
 * @date 2023/9/19
 */
class AssertRile {
    public static void main(String[] args) {
        Assert.assertEquals("assert", StringUtils.lowerCase("ASSERT"));
    }
}
