package frame.spring.pool;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.task.TaskDecorator;

import java.util.Iterator;
import java.util.List;

/**
 * @author zqw
 * @date 2023/11/5
 */
public class TaskChainDecorator implements TaskDecorator {
    private final List<ThreadPoolTaskDecorator> decorators;

    public TaskChainDecorator(List<ThreadPoolTaskDecorator> decorators) {
        this.decorators = decorators;
    }

    @Override
    @NotNull
    public Runnable decorate(@NonNull Runnable runnable) {
        return decoratorRunnable(decorators.iterator(), runnable);
    }

    Runnable decoratorRunnable(Iterator<ThreadPoolTaskDecorator> decorators, Runnable runnable) {
        Runnable delegate = runnable;
        while (decorators.hasNext()) {
            delegate = decorators.next().decorator(delegate);
        }
        return delegate;
    }
}
