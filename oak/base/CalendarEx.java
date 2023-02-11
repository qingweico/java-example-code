package oak.base;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zqw
 * @date 2021/2/10
 */
class CalendarEx {
    public static void main(String[] args) {
        Calendar s = Calendar.getInstance();
        System.out.println(s.get(Calendar.YEAR));
        System.out.println(s.get(Calendar.SECOND));
        System.out.println(s.get(Calendar.MONTH) + 1);
        // 如何格式化时期
        Date d = new Date();
        DateFormat df = DateFormat.getInstance();
        System.out.println(df.format(d));
    }
}
