package oak.base;

import util.Print;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author zqw
 * @date 2021/2/10
 */
class DateTransform {
    public static void main(String[] args) {
        // 键盘录入你的出生年月日
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你的出生日期 : (yyyy-MM-dd)");
        String s = sc.nextLine();

        // 把这个字符串转换为一个日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(s);
        } catch (ParseException e) {
            // 中止当前正在运行的java虚拟机,并正常退出
            Print.log("logback", System.Logger.Level.ERROR, "Invalid date format : " + s);
            System.exit(0);
        }
        // 通过日期获取到一个毫秒值
        long myTime = d.getTime();

        // 获取当前时间的毫秒值
        long time = System.currentTimeMillis();

        // 用D-C得到一个毫秒值
        long dateTime = time - myTime;

        // 把得到的毫秒值计算成一个天即可
        System.out.println("你来到这个世界已经 : " + (dateTime / 1000 / 60 / 60 / 24) + "天了");
        sc.close();
    }
    // parse将字符串转换为Date类型, format是将日期类型的转换为字符串类型的
}
