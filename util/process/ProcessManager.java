package util.process;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link Process} Manager
 *
 * @author zqw
 * @date 2022/7/11
 */
public class ProcessManager {

    /**
     * Singleton instance
     */
    public static final ProcessManager INSTANCE = new ProcessManager();
    private final ConcurrentMap<Process, String> unfinishedProcessesCache = Maps.newConcurrentMap();

    protected void addUnfinishedProcess(Process process, String arguments) {
        unfinishedProcessesCache.putIfAbsent(process, arguments);
    }

    protected void removeUnfinishedProcess(Process process, String arguments) {
        unfinishedProcessesCache.remove(process, arguments);
    }

    public void destroy(Process process) {
        process.destroy();
    }

    /**
     * Unfinished Processes Map
     *
     * @return non-null
     */
    @Nonnull
    public Map<Process, String> unfinishedProcessesMap() {
        return Collections.unmodifiableMap(unfinishedProcessesCache);
    }
}
