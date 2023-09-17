
package util.filter;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * {@link FilterUtils} Test Case
 *
 * @author zqw
 */
public class FilterUtilsTest {

    @Test
    public void testFilter() {
        List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> result = FilterUtils.filter(integerList, FilterOperator.AND, new Filter<Integer>() {
            @Override
            public boolean accept(Integer filteredObject) {
                return filteredObject % 2 == 0;
            }
        });

        Assert.assertEquals(Arrays.asList(0, 2, 4, 6, 8), result);

        result = FilterUtils.filter(integerList, new Filter<Integer>() {
            @Override
            public boolean accept(Integer filteredObject) {
                return filteredObject % 2 == 0;
            }
        });

        Assert.assertEquals(Arrays.asList(0, 2, 4, 6, 8), result);

        result = FilterUtils.filter(integerList, FilterOperator.OR, new Filter<Integer>() {
            @Override
            public boolean accept(Integer filteredObject) {
                return filteredObject % 2 == 1;
            }
        });

        Assert.assertEquals(Arrays.asList(1, 3, 5, 7, 9), result);
    }
}
