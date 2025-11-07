package oak.test;

import cn.hutool.Hutool;
import cn.hutool.core.collection.EnumerationIter;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.emoji.EmojiUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.setting.yaml.YamlUtil;
import cn.qingweico.collection.CollUtils;
import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import cn.qingweico.concurrent.thread.ThreadUtils;
import cn.qingweico.constants.Constants;
import cn.qingweico.constants.Symbol;
import cn.qingweico.convert.Convert;
import cn.qingweico.database.DatabaseHelper;
import cn.qingweico.database.NamedSqlTmplQuery;
import cn.qingweico.io.FileUtils;
import cn.qingweico.io.Print;
import cn.qingweico.model.HttpRequestEntity;
import cn.qingweico.model.RequestConfigOptions;
import cn.qingweico.network.NetworkUtils;
import cn.qingweico.reflect.ReflectUtils;
import cn.qingweico.serialize.SerializeUtil;
import cn.qingweico.supplier.Builder;
import cn.qingweico.supplier.ObjectFactory;
import cn.qingweico.supplier.RandomDataGenerator;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.util.MySqlUtils;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.zaxxer.hikari.HikariDataSource;
import frame.db.JdbcConfig;
import lombok.extern.slf4j.Slf4j;
import misc.CLibrary;
import object.entity.User;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.*;

/**
 * 更多测试请参考微基准测试工具jmh
 *
 * @author zqw
 * @date 2022/2/3
 */
@Slf4j
public final class BaseTest {
    @Test
    public void floatNumber() {
        float positive = 0.0F;
        float negative = -0.0F;
        // Float.NaN
        // [0x7F800001, 0x7FFFFFFF]
        // [0xFF800001, 0xFFFFFFFF]
        // Standard NaN: 0x7FC00000
        // positive == negative is true
        System.out.println(positive * negative);
        System.out.println(0x7FC00000);
        // -0.0F
        System.out.println(Float.intBitsToFloat(0x80000000));
        float positiveInfinity = 0x7F800000;
        float negativeInfinity = 0xFF800000;
        System.out.println(positiveInfinity);
        System.out.println(negativeInfinity);
        System.out.println(0x7F800001);
        System.out.println(0x7FFFFFFF);
        System.out.println(Float.NEGATIVE_INFINITY);
        System.out.println(Float.POSITIVE_INFINITY);
    }

    @Test
    public void bitComparable() {
        Integer[] a = new Integer[]{1, 8, 2, 0};
        Arrays.sort(a, (o1, o2) -> Integer.bitCount(o1) > Integer.bitCount(o2) ? o1 - o2 : o2 - o1);
        System.out.println(Arrays.toString(a));
    }

    @Test
    public void outBinary() {
        byte aByte = (byte) 0b00100001;
        System.out.println(aByte);
    }

    @Test
    public void bitOperation() {
        // -1 的原码为1000 0000 0000 0000 0000 0000 0000 0001
        // 补码为1111 1111 1111 1111 1111 1111 1111 1111
        // 左移29为 1110 0000 0000 0000 0000 0000 0000 0000
        // 结果为-2的31次方 + 2的30次方 + 2的29次方: -536870912
        System.out.println(-1 << 29);
        System.out.println(1 << 29);
        System.out.println(2 << 29);
    }

    @Test
    public void flag() {
        flag:
        for (int i = 0; i < Constants.TWO; i++) {
            for (int j = 0; j < Constants.TEN; j++) {
                if (j == 3) {
                    continue flag;
                }
                Print.grace(i, j);
            }
        }
    }

    @Test
    @SuppressWarnings("ConstantValue")
    public void isAssignableFrom() {
        // 原始类型 该Class对象和参数类型一致时才返回true
        // 对象类型 父接口或者父类都会返回true
        // true
        System.out.println(int.class.isAssignableFrom(int.class));
        // true
        // 判断参数类型是否是Class类型的相同类型或者子类型,子接口
        System.out.println(Comparable.class.isAssignableFrom(Integer.class));
        System.out.println(System.getProperty("java.class.path"));
    }

    @Test
    public void huTool() {
        Hutool.printAllUtils();
    }

    @Test
    public void classScan() {
        Set<Class<?>> classes = ClassUtil.scanPackage("effective",
                (clazz) -> (!clazz.isInterface()));
        classes.forEach(System.out::println);
    }

    @Test
    public void urlIter() {
        EnumerationIter<URL> resourceIter = ResourceUtil.getResourceIter("effective");
        while (resourceIter.hasNext()) {
            URL url = resourceIter.next();
            System.out.println(url.toString());
        }
        System.out.println(this.getClass().getClassLoader().getResource("effective"));
    }

    @Test
    public void systemClassLoader() {
        System.out.println(ClassLoader.getSystemClassLoader());
    }

    @Test
    public void timeUnit() {
        // 600s 将给定参数 转换为 unit 相对应的单位的秒数
        System.out.println(TimeUnit.MINUTES.toSeconds(10));
    }

    @Test
    public void toStr() {
        User user = ObjectFactory.create(User.class, true);
        String res = ToStringBuilder.reflectionToString(user);
        System.out.println(res);
    }

    @Test
    public void reflection() {
        User user = ObjectFactory.create(User.class, true);
        Arrays.stream(ReflectUtil.getFields(User.class)).toList()
                .stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
                .forEach(field -> Print.grace(field.getName(), ReflectUtil.getFieldValue(user, field)));
        Print.printMap(ReflectUtils.readFieldsAsMap(user));
    }

    @Test
    public void sendProxyPostTest() throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("ttl", "3");
        System.out.println(NetworkUtils.sendProxyPost("https://httpbin.org/post", body, RequestConfigOptions.builder()
                .connectTimeout(1000)
                .build()));
    }

    @Test
    public void builder() {
        User user = Builder.builder(User::new)
                .with(User::setUsername, "username")
                .with(User::setId, 1L)
                .build();
        System.out.println(user);
    }

    @Test
    public void serialize() {
        // Object
        System.out.println("Object---------------------------");
        User user = ObjectFactory.create(User.class, true);
        byte[] bytes = SerializeUtil.serialize(user);
        System.out.println(cn.qingweico.serialize.SerializeUtil.deserialize(bytes));

        System.out.println("List---------------------------");
        // List
        List<User> list = new ArrayList<>();
        int size = 5;
        for (int i = 0; i < size; i++) {
            list.add(ObjectFactory.create(User.class, true));
        }
        bytes = SerializeUtil.serializeList(list);
        List<User> users = SerializeUtil.deserializeList(bytes);
        users.forEach(System.out::println);
    }

    @Test
    public void messageFormatter() {
        String formattedMessage = MessageFormat.format("在缓冲区[{1}]位置添加[{0}]字节时发生溢出错误",
                5, "buf_read0");
        System.out.println(formattedMessage);
    }

    @Test
    public void printSystemProperties() {
        Properties properties = System.getProperties();
        properties.keySet().forEach(e -> Print.grace(e, properties.get(e)));
    }

    @Test
    public void pi() {
        System.out.println(Math.asin(1) * 2);
        System.out.println(Math.acos(-1));
        System.out.println(Math.atan(1) * 4);
        System.out.println(Math.acos(0) * 2);
    }

    @Test
    public void stringJoiner() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add("你").add("好").add("世").add("界");
        System.out.println(sj);
        System.out.println(sj.length());
    }

    /**
     * {@link StringTokenizer} 已过时, 不再建议使用, 请使用 {@link String#split(String)}
     * 或者正则工具替代, 目前大部分存在于遗留的代码, 为了向后兼容
     */
    @Test
    public void stringTokenizer() {
        String str = RandomDataGenerator.address();
        StringTokenizer tokenizer = new StringTokenizer(str, Symbol.WHITE_SPACE, false);
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
        }
    }

    @Test
    public void emoji() {
        System.out.println(EmojiUtil.isEmoji("\uD83E\uDD23"));
    }

    /**
     * @see cn.hutool.extra.compress.CompressUtil
     * @see cn.hutool.core.util.ZipUtil
     */
    @Test
    public void hutoolCompress() {

    }

    @Test
    public void hutoolThreadUtil() {
        ThreadUtil.execAsync(() -> ThreadUtils.gracePrint(RandomDataGenerator.name()));
        ThreadUtil.safeSleep(1000);
        // ...
    }

    @Test
    public void createSpringEnv() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(JdbcConfig.class);
        context.register(SpringUtil.class);
        context.register(NamedSqlTmplQuery.class);
        context.refresh();
        NamedSqlTmplQuery namedSqlTmplQuery = SpringUtil.getBean(NamedSqlTmplQuery.class);
        String sql = "select username from t_user where id = :id";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", 1L);
        // Single Column, Multip Column use queryForMap
        String result = namedSqlTmplQuery.queryForObject(sql, sqlParameterSource, "String");
        System.out.println(result);
        context.close();
    }

    @Test
    public void readExcel() throws IOException {
        byte[] bytes = HttpUtil.downloadBytes("https://go.microsoft.com/fwlink/?LinkID=521962");
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            Print.println(reader.readAsText(false));
        }
    }

    @Test
    public void cancelFuture() throws InterruptedException {
        ExecutorService pool = ThreadPoolBuilder.create();
        Future<String> future = pool.submit(() -> {
            Duration of = Duration.of(1, ChronoUnit.SECONDS);
            for (int i = 0; i < 10; i++) {
                org.apache.commons.lang3.ThreadUtils.sleep(of);
                log.info("For loop {}", i);
            }
            return "complete!";
        });
        org.apache.commons.lang3.ThreadUtils.sleep(Duration.of(3, ChronoUnit.SECONDS));
        // mayInterruptIfRunning
        // true 通过interrupt终止当前正在运行的任务
        // false 则等到当前任务全部完成后cancel
        future.cancel(true);
        System.out.println(future.isCancelled());
        System.out.println(future.isDone());
        pool.shutdown();
        if (pool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("thread pool termination!");
        }
    }

    @Test
    public void getInputStreamByUrl() throws IOException {
        try (InputStream inputStream = NetworkUtils.getInputStreamByUrl(HttpRequestEntity.builder()
                .requestUrl("https://www.baidu.com")
                .build())) {
            System.out.println(FileCopyUtils.copyToString(new InputStreamReader(inputStream)));
        }
    }

    @Test
    public void downloadFileByUrl() throws IOException {
        HttpRequestEntity hre = HttpRequestEntity.builder()
                .requestUrl("https://download.microsoft.com/download/1/4/E/14EDED28-6C58-4055-A65C-23B4DA81C4DE/Financial%20Sample.xlsx")
                .build();
        File tempFile = new File("temp_excel.xlsx");
        FileUtil.writeFromStream(NetworkUtils.getInputStreamByUrl(hre), tempFile);
    }

    @Test
    public void reflect() throws ClassNotFoundException {
        Class<?> forName = ClassUtils.forName(org.apache.commons.lang3.ClassUtils.getName(this.getClass()), ClassUtils.getDefaultClassLoader());
        Method[] declaredMethods = forName.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(System.out::println);
        Method loadMethod = ClassUtils.getMethod(BaseTest.class, "reflect");
        Test test = AnnotationUtils.findAnnotation(loadMethod, Test.class);
        if (test != null) {
            Print.print(AnnotationUtils.getAnnotationAttributes(test));
        }
    }

    @Test
    public void zipFile() throws IOException {
        try (ZipFile zipFile = ZipUtil.toZipFile(new File("lib/InstrumentationAgent.jar"), StandardCharsets.UTF_8)) {
            final Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
            while (zipEntries.hasMoreElements()) {
                final ZipEntry zipEntry = zipEntries.nextElement();
                System.out.println(zipEntry.getName());
            }
        }
        System.out.println("--------------------");
        ZipInputStream zis = new ZipInputStream(new FileInputStream("lib/InstrumentationAgent.jar"));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            System.out.println(entry.getName());
        }
    }

    /**
     * 使用 Deflater 压缩数据
     * 使用 Inflater 解压数据
     */
    @Test
    public void deflater() {
        String inputString = "Hello, this is a test string to compress using Deflater.";
        byte[] input = inputString.getBytes();
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        try (ByteArrayOutputStream compressedOutputStream = new ByteArrayOutputStream(input.length)) {
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int compressed = deflater.deflate(buffer);
                compressedOutputStream.write(buffer, 0, compressed);
            }
            byte[] compressedOutput = compressedOutputStream.toByteArray();
            log.info("Original : {}, Size : {}", new String(input), input.length);
            // 查看压缩后的二进制字节数组, 使用Base64或者十六进制
            log.info("Compressed : \nBase64 -> {}\nHex -> {}, Size : {}", Base64.getEncoder().encodeToString(compressedOutput),
                    Hex.toHexString(compressedOutput), compressedOutput.length);
            Inflater inflater = new Inflater();
            inflater.setInput(compressedOutput);
            try (ByteArrayOutputStream decompressedOutputStream = new ByteArrayOutputStream(compressedOutput.length);) {
                while (!inflater.finished()) {
                    int uncompressed = inflater.inflate(buffer);
                    decompressedOutputStream.write(buffer, 0, uncompressed);
                }
                String decompressedOutput = decompressedOutputStream.toString();
                log.info("Decompressed : {}", decompressedOutput);
            } catch (IOException | DataFormatException e) {
                log.info(e.getMessage(), e);
            }
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
    }

    @Test
    public void tmpdir() throws IOException {
        String tmpdir = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis() + ".txt";
        FileCopyUtils.copy("tmpdir".getBytes(), new FileOutputStream(tmpdir));
        System.out.println(FileCopyUtils.copyToString(new FileReader(tmpdir)));
        FileUtils.delete(tmpdir);
    }

    @Test
    public void httpRequest() {
        Map<String, String> requestProperty = new HashMap<>();
        requestProperty.put(Header.ACCEPT.getValue(), ContentType.JSON.getValue());
        requestProperty.put(Header.ACCEPT_LANGUAGE.getValue(), "zh-CN,zh;q=0.90");
        String token = RandomStringUtils.random(24, true, true);
        requestProperty.put(Header.AUTHORIZATION.getValue(), "Bearer " + token);
        Map<String, String> requestBody = new HashMap<>();
        CollUtils.fillMap(requestBody, 10, () -> UUID.randomUUID().toString(), () -> RandomStringUtils.random(12, true, true));
        HttpRequestEntity hre = HttpRequestEntity.builder()
                .requestUrl("https://httpbin.org/bearer")
                .requestHeaders(requestProperty)
                .build();
        System.out.println(NetworkUtils.httpRequest(hre));
    }

    @Test
    public void mysqlDatasource() throws SQLException {
        DataSource dataSource = DatabaseHelper.getDatasource();
        System.out.println(MySqlUtils.showTables(dataSource.getConnection()));
        System.out.println(JdbcUtils.executeQuery(dataSource, "select 1"));
    }

    @Test
    public void hikari() throws SQLException {
        HikariDataSource dataSource = DatabaseHelper.getHikari();
        System.out.println(MySqlUtils.showTables(dataSource.getConnection()));
        dataSource.close();
    }

    @Test
    public void stackTrace() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement.toString());
        }
    }

    @Test
    public void fileStore() throws IOException {
        // 获取 WindowsFileStore
        FileStore store = Files.getFileStore(Paths.get("C://"));
        // JrtFileStore 不支持getTotalSpace(UnsupportedOperationException)
        // 通过使用 FileSystemsTest#zipFileSystem 创建 ZipFileSystem, 再获取 ZipFileStore
        System.out.println(Convert.byteCountToDisplaySize(store.getTotalSpace()));
    }

    @Test
    public void verbosePrint() {
        Map<String, Object> map = new HashMap<>();
        CollUtils.fillMap(map, 10, () -> UUID.randomUUID().toString(), () -> RandomStringUtils.randomAlphanumeric(16));
        MapUtils.verbosePrint(System.out, null, map);
    }

    /**
     * @see org.yaml.snakeyaml.resolver.Resolver 中 no 会被解析为 bool
     */
    @Test
    public void yamlResolver() {
        Dict dict = YamlUtil.load(new StringReader("""
                    port: 8080
                    pic:
                       no: xxx
                """));
        Print.print(dict);
    }

    @Test
    public void jna() {
        Pointer timePtr = new Memory(Native.LONG_SIZE);
        long time = CLibrary.INSTANCE.time(timePtr);
        System.out.println("timestamp: " + time);
        Pointer tmPtr = CLibrary.INSTANCE.localtime(timePtr);
        CLibrary.tm timeinfo = new CLibrary.tm(tmPtr);

        System.out.println("Current local time: " +
                (timeinfo.tm_year + 1900) + "-" +
                (timeinfo.tm_mon + 1) + "-" +
                timeinfo.tm_mday + " " +
                timeinfo.tm_hour + ":" +
                timeinfo.tm_min + ":" +
                timeinfo.tm_sec);


        Memory buffer = new Memory(32);
        NativeLong result = CLibrary.INSTANCE.strftime(buffer, new NativeLong(32), "%Y-%m-%d %H:%M:%S", timeinfo);

        if (result.intValue() > 0) {
            System.out.println("Formatted time: " + buffer.getString(0));
        }
    }

    @Test
    public void oshi() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        GlobalMemory memory = hardware.getMemory();
        System.out.println(Convert.byteCountToDisplaySize(memory.getTotal()));
        System.out.println(memory.getPhysicalMemory());
        System.out.println(Convert.byteCountToDisplaySize(memory.getAvailable()));
    }
}
