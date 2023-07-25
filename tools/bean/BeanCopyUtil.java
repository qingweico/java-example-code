package tools.bean;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.SimpleCache;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Converter;
import object.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import util.ObjectFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 各种 Bean 属性拷贝工具
 * @author zqw
 * @date 2023/2/10
 * @see org.springframework.beans.BeanUtils
 * @see cn.hutool.core.bean.BeanUtil
 * @see org.apache.commons.beanutils.BeanUtils
 * @see org.apache.commons.beanutils.PropertyUtils
 * @see org.springframework.cglib.beans.BeanCopier
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanCopyUtil {
    public static void main(String[] args) {
        User user = ObjectFactory.create(User.class, true);


        UserDto userDto = new UserDto();

        // Spring
        BeanUtils.copyProperties(user, userDto);
        System.out.println(userDto);

        // CGLIB BeanCopier
        // VM options : --add-opens java.base/java.lang=ALL-UNNAMED
        // 使用转换器可以实现不同类型的拷贝
        // BeanCopier只拷贝名称和类型都相同的属性
        BeanCopier copier = BeanCopier.create(User.class, UserDto.class, false);
        userDto = new UserDto();
        copier.copy(user, userDto, null);

        // TODO

        // mapstruct
    }


    /**单对象拷贝*/

    public static <T, V> V copy(T source, V desc) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }
        if (ObjectUtil.isNull(desc)) {
            return null;
        }
        BeanCopier beanCopier = BeanCopierCache.INSTANCE.get(source.getClass(), desc.getClass(), null);
        beanCopier.copy(source, desc, null);
        return desc;
    }

    /**单对象拷贝(基于Class类型)*/

    public static <T, V> V copy(T source, Class<V> desc) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }
        if (ObjectUtil.isNull(desc)) {
            return null;
        }
        final V target = ReflectUtil.newInstanceIfPossible(desc);
        return copy(source, target);
    }

    /**多对象拷贝(基于Class类型)*/

    public static <T, V> List<V> copyList(List<T> sourceList, Class<V> desc) {
        if (ObjectUtil.isNull(sourceList)) {
            return null;
        }
        if (CollUtil.isEmpty(sourceList)) {
            return CollUtil.newArrayList();
        }
        return sourceList.stream()
                .map(source -> {
                    V target = ReflectUtil.newInstanceIfPossible(desc);
                    copy(source, target);
                    return target;
                }).collect(Collectors.toList());
    }

    public enum BeanCopierCache {

        /**
         * Instance
         */
        INSTANCE;

        private final SimpleCache<String, BeanCopier> cache = new SimpleCache<>();

        /**XXX*/

        public BeanCopier get(Class<?> sourceClass, Class<?> targetClass, Converter converter) {
            final String key = genKey(sourceClass, targetClass, converter);
            return cache.get(key, () -> BeanCopier.create(sourceClass, targetClass, converter != null));
        }


        /**XXX*/

        private String genKey(Class<?> sourceClass, Class<?> targetClass, Converter converter) {
            StringBuilder key = StrUtil.builder()
                    .append(sourceClass.getName())
                    .append('#')
                    .append(targetClass.getName());
            Optional.ofNullable(converter)
                    .ifPresent(c -> key.append(c.getClass().getName()));
            return key.toString();
        }
    }
}
