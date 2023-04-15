package util;

import object.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 对象建造者模式
 *
 * @author zqw
 * @date 2023/1/1
 */
public class Builder<T> {


    private final Supplier<T> constructor;
    private final List<Consumer<T>> dInjects = new ArrayList<>();


    public Builder(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    public static <T> Builder<T> builder(Supplier<T> constructor) {
        return new Builder<>(constructor);
    }

    public <U> Builder<T> with(BiConsumer<T, U> consumer, U u) {
        Consumer<T> c = instance -> consumer.accept(instance, u);
        dInjects.add(c);
        return this;
    }

    public T build() {
        T instance = constructor.get();
        dInjects.forEach(dInject -> dInject.accept(instance));
        return instance;
    }

    public static void main(String[] args) {
        User user = Builder.builder(User::new)
                .with(User::setUsername, "username")
                .with(User::setId, 1L)
                .build();
        System.out.println(user);
    }
}
