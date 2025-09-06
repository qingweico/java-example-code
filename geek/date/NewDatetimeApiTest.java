package geek.date;

import annotation.Pass;
import org.junit.Test;
import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * source : [极客时间] [Java 业务开发常见错误 100 例] [16讲]
 * {<a href="https://time.geekbang.org/column/article/224240"></a>}
 * @author zqw
 * @date 2022/12/28
 * {@link Date}
 * {@link Calendar}
 * {@link SimpleDateFormat}
 * Java 8 之前处理时间的 API 缺点 : 可读性差;易用性差;使用起来冗余繁琐,还有线程安全问题
 * ******************** Java 8 新的时间日期类 ********************
 * {@link ZoneId} 表示 UTC 的时区
 * {@link ZoneOffset} 表示 UTC 时区的偏移量
 * {@link Period} 定义了日期间隔
 * {@link LocalDateTime} 不带有时区属性,只能认为是一个时间表示,所以命名为本地时区的日期时间
 * {@link ZonedDateTime} ZonedDateTime = LocalDateTime + ZoneId, 具有时区属性,才是一个有效的时间
 * {@link DateTimeFormatter}
 */
@Pass
@SuppressWarnings("all")
public final class NewDatetimeApiTest {

    @Test
    public void initDate() {

        Date date = new Date(2019, 12, 31, 11, 12, 13);
        // Sat Jan 31 11:12:13 CST 3920
        // 使用 Date 初始化时间的问题是 : 年份应该是和 1900 的差值 而月份则是0到11
        System.out.println(date);
        date = new Date(2019 - 1900, 11, 31, 11, 12, 13);
        System.out.println(date);
    }

    @Test
    public void calendar() {
        Calendar calendar = Calendar.getInstance();
        // 初始化时年参数直接使用当前年即可
        calendar.set(2019, 11, 31, 11, 12, 13);
        System.out.println(calendar.getTime());
        calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        // 可以直接使用 Calendar.DECEMBER 来初始化月份
        calendar.set(2019, Calendar.DECEMBER, 31, 11, 12, 13);
        System.out.println(calendar.getTime());
    }

    @Test
    public void timeZoneProblem() {
        /*关于时区问题*/

        // 1 : Date类并无时区问题,世界上任何一台计算机使用 new Date() 初始化得到的时间都一样
        // 因为 Date 中保存的是 UTC 时间,UTC 是以原子钟为基础的统一时间,不以太阳参照计时,并无时区划分

        // 2 : Date 中保存的是一个时间戳,代表的是从 1970 年 1 月 1 日 0 点(Epoch 时间)到现在的毫秒数


        // 中国上时区相比 UTC 时差 +8 小时 所以 : Thu Jan 01 08:00:00 CST 1970
        System.out.println(new Date(0));
        Print.grace(TimeZone.getDefault().getID(), TimeZone.getDefault().getRawOffset() / 3600000);

        /*保存日期时间的两种方式*/

        // 1 : 以 UTC 保存,保存的时间没有时区属性,是不涉及时区时间差问题的世界统一时间
        // 我们通常说的时间戳,或 Java 中的 Date 类就是用的这种方式,这也是推荐的方式

        // 2 : 以字面量保存,比如年 / 月 / 日 时: 分: 秒,一定要同时保存时区信息;只有有了时区信息,我们才能知道这个字面量时间真正的时间点,
        // 否则它只是一个给人看的时间表示,只在当前时区有意义 Calendar 是有时区概念的,所以我们通过不同的时区初始化 Calendar,
        // 得到了不同的时间
    }

    /*时间字面量转换成时间在不同的时区中存在的问题*/

    @Test
    public void dateLiteralsTranslatedTime() throws ParseException {

        // 对于同一个本地时间的表示,不同时区的人解析得到的 UTC 时间一定是不同的,反过来不同的本地时间可能对应同一个 UTC
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 默认时区解析时间表示
        Date date = inputFormat.parse(stringDate);
        Print.grace(date, date.getTime());
        // 纽约时区解析时间表示
        inputFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        date = inputFormat.parse(stringDate);
        Print.grace(date, date.getTime());
    }

    /*时间格式化为字面量出现的错乱*/

    @Test
    public void timeFormattedLiterals() throws ParseException {

        // 同一个 Date,在不同的时区下格式化得到不同的时间表示
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = inputFormat.parse(stringDate);
        // 默认时区格式化输出
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date));
        // 纽约时区格式化输出
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date));

        // UTC 时间需要根据当前时区解析为正确的本地时间
    }

    /*解决时区问题*/

    // 要正确处理时区,在于存进去和读出来两方面 : 存的时候,需要使用正确的当前时区来保存,这样 UTC 时间才会正确;读的时候,
    // 也只有正确设置本地时区,才能把 UTC 时间转换为正确的当地时间

    @Test
    public void newDateApi() {

        String stringDate = "2020-01-02 22:00:00";
        // 初始化三个时区
        // [使用 ZoneId.of 来初始化一个标准的时区,也可以使用 ZoneOffset.ofHours 通过一个 offset,来初始化一个具有指定时间差的自定义时区]
        ZoneId timeZoneSh = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNy = ZoneId.of("America/New_York");
        ZoneId timeZoneJst = ZoneOffset.ofHours(9);
        // 格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.parse(stringDate, dateTimeFormatter), timeZoneJst);
        // 使用DateTimeFormatter格式化时间,可以通过withZone方法直接设置格式化使用的时区
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        Print.grace(timeZoneSh.getId(), outputFormat.withZone(timeZoneSh).format(date));
        Print.grace(timeZoneNy.getId(), outputFormat.withZone(timeZoneNy).format(date));
        Print.grace(timeZoneJst.getId(), outputFormat.withZone(timeZoneJst).format(date));
        // 对于国际化问题,使用 Java8 新时间日期类
        // 即使用 ZonedDateTime 保存时间,然后使用设置了 ZoneId 的 DateTimeFormatter 配合 ZonedDateTime 进行时间格式化得到本地时间表示
    }

    /*使用 SimpleDateFormat 格式化时间的问题*/

    @Test
    public void sfdFormattedTimeProblem() {

        // 区域为zh_CN
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        // 区域改为法国
        Locale.setDefault(Locale.FRANCE);
        System.out.println(Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 29, 0, 0, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println(sdf.format(calendar.getTime()));
        System.out.println(calendar.getWeekYear());
        // 周日为一周的第一天 使用1表示 {@link java.util.Calendar.SUNDAY}
        System.out.println(calendar.getFirstDayOfWeek());
        System.out.println(calendar.getMinimalDaysInFirstWeek());

        // [JDK SimpleDateFormatter 文档] : https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html
        // 小写 y 是年,而大写 Y 是 week year,也就是所在的周属于哪一年
        // 一年第一周的判断方式是 : 从 getFirstDayOfWeek() 开始,完整的 7 天,并且包含那一年至少 getMinimalDaysInFirstWeek() 天
        // [这个计算方式和区域相关]

        // tips : 针对年份的日期格式化,应该一律使用 y 而非 Y
    }

    /*定义的 static 的 SimpleDateFormat 可能会出现线程安全问题*/

    private static final ThreadLocal<SimpleDateFormat> THREAD_SAFE_SIMPLE_DATE_FORMAT =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Test
    public void sdfThreadSafetyProblem() throws InterruptedException {

        // SimpleDateFormat 的作用是定义解析和格式化日期时间的模式,但它的解析和格式化操作是非线程安全的
        // 原因 : SimpleDateFormat 的 parse 方法调用 CalendarBuilder 的 establish 方法来构建 Calendar;establish
        // 方法内部先清空 Calendar 再构建 Calendar,整个操作没有加锁,可能会产生一个线程还没来得及处理 Calendar 就被另一个线程清空了的情况
        // format 方法也类似
        // 解决 : 通过 ThreadLocal 来存放 SimpleDateFormat
        int tc = 20;
        ExecutorService pool = ThreadPoolBuilder
                .builder()
                .maxPoolSize(20)
                .corePoolSize(20)
                .preStartAllCore(true)
                .build();
        for (int i = 0; i < tc; i++) {
            pool.execute(() -> {
                for (int j = 0; j < Constants.TEN; j++) {
                    try {
                        System.out.println(THREAD_SAFE_SIMPLE_DATE_FORMAT.get().parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        Print.err(e.getMessage());
                    }
                }
            });
        }
        pool.shutdown();
        if (pool.awaitTermination(1, TimeUnit.HOURS)) {
            System.out.println("It's Over!");
        }
    }


    /*当需要解析的字符串和格式不匹配的时候,SimpleDateFormat 表现得很宽容*/


    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR)
            .appendLiteral("/")
            .appendValue(ChronoField.MONTH_OF_YEAR)
            .appendLiteral("/")
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral(" ")
            .appendValue(ChronoField.HOUR_OF_DAY)
            .appendLiteral(":")
            .appendValue(ChronoField.MINUTE_OF_HOUR)
            .appendLiteral(":")
            .appendValue(ChronoField.SECOND_OF_MINUTE)
            .appendLiteral(".")
            .appendValue(ChronoField.MILLI_OF_SECOND)
            .toFormatter();

    @Test
    public void literalsMismatchingFormatter() throws ParseException {

        String dateString = "20160901";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        // result:Mon Jan 01 00:00:00 CST 2091
        // 原因 : 0901 被当成了月份
        // 解决 : 使用 DateTimeFormatter
        // 关于 DateTimeFormatter 类 : DateTimeFormatter 是线程安全的,可以定义为 static 使用;最后,DateTimeFormatter
        // 的解析比较严格,需要解析的字符串和格式不匹配时,会直接报错
        System.out.println(dateFormat.parse(dateString));

        LocalDateTime localDateTime = LocalDateTime.parse("2020/1/2 12:34:56.789", DATE_TIME_FORMATTER);
        System.out.println(localDateTime.format(DATE_TIME_FORMATTER));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        // java.time.format.DateTimeParseException: Text '20160901' could not be parsed at index 0
        System.out.println(dateTimeFormatter.parse(dateString));
    }

    /*日期时间的计算*/

    @Test
    public void calculateDatetime() {
        // 使用时间戳进行时间计算

        Date today = new Date();
        Date nextMonth = new Date(today.getTime() + 30L * 1000 * 60 * 60 * 24);
        System.out.println(today);
        // 原因 : int 发生了溢出 导致得到的日期比当前日期还要早
        // 解决 ：使用 long 即可
        System.out.println(nextMonth);

        // 在 Java 8 之前,对时间日期的计算应该使用Calendar类

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        // 一个月之后
        c.add(Calendar.DAY_OF_MONTH, 30);
        System.out.println(c.getTime());


        /*对日期时间做计算操作,Java 8 日期时间 API 会比 Calendar 功能强大很多*/


        System.out.println(LocalDate.now()
                // 减一天
                .minus(Period.ofDays(1))
                // 加一天
                .plus(1, ChronoUnit.DAYS)
                // 减一个月
                .minusMonths(1)
                // 加一个月
                .plus(Period.ofMonths(1)));
    }

    /*通过 with 方法进行快捷时间调节*/

    @Test
    public void withLocalDateMethod() {

        // 本月的第一天
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));

        // 相加天数
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).plusDays(30));

        // 今天之前的一个周六
        System.out.println(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)));

        // 本月最后一个工作日
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));

        /*使用 lambda 表达式进行自定义的时间调整*/

        // 为当前时间增加 100 天以内的随机天数
        System.out.println(LocalDate.now().with(temporal -> temporal
                .plus(ThreadLocalRandom.current().nextInt(100), ChronoUnit.DAYS)));

        // query 方法查询是否匹配条件
        System.out.println(LocalDate.now().query(NewDatetimeApiTest::isSpringtime));
    }


    /*判断日期是否符合某个条件*/

    public static Boolean isSpringtime(TemporalAccessor date) {
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        if (month == Month.JANUARY.getValue()) {
            return Boolean.TRUE;
        }
        if (month == Month.FEBRUARY.getValue()) {
            return Boolean.TRUE;
        }
        if (month == Month.MARCH.getValue()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /*Period.between 得到了两个 LocalDate 的差,返回的是两个日期差几年零几月零几天;如果希望得知两个日期之间差几天,
    直接调用 Period 的 getDays() 方法得到的只是最后的零几天,而不是算总的间隔天数*/

    @Test
    public void period() {

        LocalDate today = LocalDate.of(2022, 12, 30);
        LocalDate specifyDate = LocalDate.of(2021, 12, 20);
        System.out.println(Period.between(specifyDate, today).getDays());
        System.out.println(Period.between(specifyDate, today));
        // 解决 : 使用 ChronoUnit.DAYS.between(Temporal,Temporal) 方法
        System.out.println(ChronoUnit.DAYS.between(specifyDate, today));
    }

    /*Date 类型和 LocalDatetime类型之间的相互转换*/


    @Test
    public void dateAndLocalDatetimeInterconversion() {

        // Date 和 LocalDatetime 的区别:
        // 虽然它们都没有时区概念,但 java.util.Date 类是因为使用 UTC 表示,所以没有时区概念,其本质是时间戳;而 LocalDateTime,
        // 严格上可以认为是一个日期时间的表示,而不是一个时间点


        // Date -> LocalDatetime; 使用 toInstant 将 Date 转换为 UTC 时间戳并提供当前时区

        // LocalDatetime -> Date; 使用 atZone 方法把 LocalDateTime 转换为 ZonedDateTime, 然后才能获得 UTC 时间戳

        Date in = new Date();
        // toInstant 的作用 :  旧的时间 Date API 与 新的时间 API 之间的转换桥梁
        // 将 Date 对象转换为 JDK8 新日期 API 的中的 Instant 对象(即JDK引入的一个新对象, 表示精确到纳秒的时间戳)

        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(out);

        System.out.println(TimeZone.getDefault().getID());
        System.out.println(TimeZone.getDefault().toZoneId());
        System.out.println(ZoneId.systemDefault());
    }

    /* 对于 Date 是一个时间戳,是 UTC 时间,没有时区概念,但是调用 toString 方法会输出带有时区的信息
       是因为toString方法中normalize方法会获取当前的默认时区*/


    /*Date 和 LocalDateTime 关于时间的加减运算*/

    @Test
    public void arithmeticTime() {
        // Date
        Date d = Date.from(Instant.now());
        // GregorianCalendar 是公历(格里高利历)的具体实现类
        // 支持儒略历(Julian Calendar)向公历的转换
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        // ----------------- 添加操作 -----------------
        // 设置年
        calendar.add(Calendar.YEAR, 1);
        // 设置月
        calendar.add(Calendar.MONTH, 1);
        // 设置日
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        // 设置星期
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        // 本年中已过去多少天
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        // 二十四小时制的小时
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        // 十二小时制的小时
        calendar.add(Calendar.HOUR, 1);
        // 分钟
        calendar.add(Calendar.MINUTE, 1);
        // 秒
        calendar.add(Calendar.SECOND, 1);
        // 毫秒
        calendar.add(Calendar.MILLISECOND, 1);
        d = calendar.getTime();
        System.out.println(d);


        // LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse("2023-08-10 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime dateThirtyDayAgo = dateTime.minusDays(31);
        dateThirtyDayAgo = dateThirtyDayAgo.withHour(12)
                .withMinute(12)
                .withSecond(12);
        String formatted = dateThirtyDayAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(formatted);
    }
}
