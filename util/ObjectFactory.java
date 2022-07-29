package util;

import annotation.Ignore;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * --------------- 对象创建和属性填充工厂 ---------------
 *
 * @author zqw
 * @date 2022/7/16
 */
@Slf4j
public class ObjectFactory {
    public static <T> T create(Class<T> type, boolean isPopulate) {
        if (type == null) {
            return null;
        }
        T instance = null;
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            // 1 对于invoke包级私有的(顶层)类还是public公有类(顶层),如果在同一个包下,除了private的构造函数不需要以下操作
            // 2 对于invoke包级私有的(顶层)类还是public公有类(顶层),如果不在同一个包下除了public都需要以下操作
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            instance = constructor.newInstance();
        } catch (Exception e) {
            log.error("instantiate {} error!, {}", type, e.getMessage());
        }
        if (isPopulate) {
            populate(instance, type);
        }
        return instance;
    }

    public static <T> T create(Class<T> type) {
        return create(type, false);
    }

    public static <T> void populate(T instance, Class<T> type) {
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (!field.canAccess(instance)) {
                field.setAccessible(true);
            }
            // 忽略掉@Ignore注解标注的属性
            if (field.getAnnotation(Ignore.class) != null) {
                continue;
            }
            try {
                if (Constants.BOOL.equalsIgnoreCase(field.getType().getSimpleName())) {
                    field.set(instance, RandomDataGenerator.tf());
                }
                if (Constants.INTEGER.equalsIgnoreCase(field.getType().getSimpleName())) {
                    field.set(instance, RandomDataGenerator.randomInt());
                }
                if (Constants.STRING.equalsIgnoreCase(field.getType().getSimpleName())) {
                    field.set(instance, RandomDataGenerator.randomName());
                }
                if (Constants.LONG.equalsIgnoreCase(field.getType().getSimpleName())) {
                    field.set(instance, SnowflakeIdWorker.nextId());
                }
                if (Constants.DATE.equalsIgnoreCase(field.getType().getSimpleName())) {
                    field.set(instance, RandomDataGenerator.randomDate());
                }
            } catch (IllegalAccessException e) {
                log.error("populate {} exception!, {}", instance, e.getMessage());
            }
        }
    }
}
