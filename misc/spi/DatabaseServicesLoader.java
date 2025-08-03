package misc.spi;

import cn.qingweico.concurrent.thread.ThreadUtils;
import cn.qingweico.concurrent.UnsafeSupport;
import cn.qingweico.utils.resource.ServiceLoaderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author zqw
 * @date 2025/7/23
 */
public class DatabaseServicesLoader {
    public static void main(String[] args) {
        SpiValidator.validate(DatabaseDriver.class);
        List<DatabaseDriver> databaseDrivers = ServiceLoaderUtils.loadServicesList(ThreadUtils.getClassLoader(), DatabaseDriver.class)
                .stream()
                .sorted((a, b) -> b.getClass().getName().compareTo(a.getClass().getName()))
                .toList();
        List<CompletableFuture<Void>> futures = new ArrayList<>(databaseDrivers.size());
        for (DatabaseDriver dd : databaseDrivers) {
            futures.add(CompletableFuture.runAsync(() -> {
                if (dd instanceof MysqlDriver) {
                    dd.connect("jdbc:mysql://localhost:3306");
                    UnsafeSupport.shortWait(2500);
                    dd.disconnect();
                } else if (dd instanceof SqlServerDriver) {
                    dd.connect("jdbc:sqlserver://localhost:1433");
                    UnsafeSupport.shortWait(3500);
                    dd.disconnect();
                }
            }));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
