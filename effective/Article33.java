package effective;

import annotation.SafeType;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 优先考虑类型安全的异构容器
 *
 * @author zqw
 * @date 2021/11/19
 */
class Article33 {

    /**
     * {@link AnnotatedElement#getAnnotation}
     * bounded type token
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {

        return null;
    }


    /**
     * Use of asSubclass to safety cast to a bounded type token
     */
    static Annotation getAnnotation(AnnotatedElement element, String annotationTypeName) {
        // Unbounded type token
        Class<?> annotationType;
        try {
            annotationType = Class.forName(annotationTypeName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return element.getAnnotation(annotationType.asSubclass(Annotation.class));
    }
}

class Favorite {
    private final Map<Class<?>, Object> favorites = new HashMap<>();

    /**
     * Typesafe heterogeneous container pattern - implementation
     */
    @SafeType
    public <T> void putFavorite(Class<?> type, T instance) {
        favorites.put(Objects.requireNonNull(type), instance);
        /*Achieving runtime type safety with a dynamic cast*/
        favorites.put(type, type.cast(instance));
    }

    @SafeType
    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }
}