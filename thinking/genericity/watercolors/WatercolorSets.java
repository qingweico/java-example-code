package thinking.genericity.watercolors;

import cn.qingweico.collection.SetUtils;
import cn.qingweico.io.Print;

import java.util.EnumSet;
import java.util.Set;

import static thinking.genericity.watercolors.Watercolors.*;

/**
 * @author zqw
 * @date 2021/4/11
 */
class WatercolorSets {
    public static void main(String[] args) {
        Set<Watercolors> set1 = EnumSet.range(BRILLIANT_RED, VIRIDIAN_HUE);
        Set<Watercolors> set2 = EnumSet.range(CERULEAN_BLUE_HUE, BURNT_UMBER);
        Print.println("set1: " + set1);
        Print.println("set2: " + set2);
        // 并集
        Print.println("union(set1, set2): " + SetUtils.union(set1, set2));
        // 交集
        Set<Watercolors> subset = SetUtils.intersection(set1, set2);
        Print.println("intersection(set1, set2): " + subset);
        // 差集
        Print.println("difference(set1, subset): " + SetUtils.difference(set1, subset));
        Print.println("difference(set2, subset): " + SetUtils.difference(set2, subset));
        // 补集
        Print.println("complement(set1, set2): " + SetUtils.complement(set1, set2));
    }
}
