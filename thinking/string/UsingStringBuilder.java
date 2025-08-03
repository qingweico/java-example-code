package thinking.string;

import cn.qingweico.constants.Constants;

import java.util.Random;

/**
 * @author zqw
 * @date 2021/2/5
 */
class UsingStringBuilder {
    public static Random random = new Random();

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < Constants.TEN; i++) {
            result.append(random.nextInt(5));
            result.append(", ").append(i);

        }
        // [)
        result.delete(result.length() - 2, result.length());
        result.append("]");
        return result.toString();
    }

    public static void main(String[] args) {
        UsingStringBuilder usb = new UsingStringBuilder();
        System.out.println(usb);
    }
}
