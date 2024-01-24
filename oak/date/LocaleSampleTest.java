package oak.date;

import org.junit.Test;
import util.Print;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @see java.util.Locale 用来国际化数据
 * @author zqw
 * @date 2023/8/1
 */
public class LocaleSampleTest {
    @Test
    public void allLocals() {
        // 获取所有可用的 Locale 对象
         Print.printArray(Locale.getAvailableLocales(), true);
    }

    @Test
    public void getDefault() {
        System.out.println(Locale.getDefault());
        System.out.println(Locale.getDefault(Locale.Category.FORMAT));
        Print.printArray(TimeZone.getAvailableIDs(), true);
        Print.printArray(Calendar.getAvailableLocales(), true);
        Print.printColl(Calendar.getAvailableCalendarTypes());
    }
    @Test
    public void timeAndLocale() {
        // 构造函数
        // Locale(language, country)

        Locale zh = Locale.getDefault();
        // zh_CN
        System.out.println(zh);
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        System.out.println(Locale.SIMPLIFIED_CHINESE);
        zh = new Locale("zh", "CN");


        Locale us = new Locale("en", "US");
        Locale jp = new Locale("ja", "JP");
        Date now = new Date();
        String zhLocale = DateFormat.getDateInstance(DateFormat.LONG, zh).format(now);
        String usLocale = DateFormat.getDateInstance(DateFormat.LONG, us).format(now);
        String jpLocale = DateFormat.getDateInstance(DateFormat.LONG, jp).format(now);

        System.out.println(zhLocale);
        System.out.println(usLocale);
        System.out.println(jpLocale);
    }
}
