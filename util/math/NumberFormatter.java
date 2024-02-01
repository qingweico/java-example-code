package util.math;

import java.text.DecimalFormat;

/**
 * @author zqw
 * @date 2024/1/30
 */
public final class NumberFormatter {

    /**
     * 格式化double类型数据
     * pattern : #,###.00 : 千位分分割, 并保留两位小数, 不足两位则补0
     * #.00 : 小数位不足两位则补0
     * #.## : 最多显示两位小数, 不会添加额外的0
     * @param number double
     * @return String
     */
    public static String fixDouble(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(number);
    }
}
