package tools.lombok;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import object.entity.User;
import util.RandomDataUtil;
import util.SnowflakeIdWorker;

import java.util.HashSet;
import java.util.Set;

/**
 * @since jdk6 JSR 269 Pluggable Annotation Processing API
 * Lombok 实现了该规范, 对源代码的抽象语法树分析并修改生成字节码文件
 * {@link lombok.EqualsAndHashCode}
 * {@link RequiredArgsConstructor 生成包含final和@NonNull注解的成员变量的构造器}
 * {@link lombok.extern.java.Log}
 * {@link SneakyThrows 对受检异常进行捕捉并抛出}
 *
 *
 * @author zqw
 * @date 2022/6/25
 */
@Slf4j
public class LombokCallSuper {
    public static void main(String[] args) {
        String address = RandomDataUtil.address();
        SuperUser s1 = new SuperUser(1L, "s1", false, address);
        SuperUser s2 = new SuperUser(2L, "s2", true, address);
        // true
        // @EqualsAndHashCode(callSuper = false) 默认为false; 不会调用父类的属性
        log.info("s1.equals(s2): {}", s1.equals(s2));
        Set<SuperUser> set = new HashSet<>();
        set.add(s2);
        set.add(s1);
        // 1
        log.info("set size: {}, set: [{}]", set.size(), set);


        String username = RandomDataUtil.name();
        User u1 = new User();
        u1.setId(SnowflakeIdWorker.nextId());
        u1.setUsername(username);
        User u2 = new User();
        u2.setId(SnowflakeIdWorker.nextId());
        u2.setUsername(username);
        System.out.printf("u1: %s%n", u1);
        System.out.printf("u2: %s%n", u2);
        // true
        System.out.println(u1.equals(u2));
    }
}

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Setter
class SuperUser extends User {
    private String address;

    public SuperUser(Long id, String name, boolean isVip, String address) {
        super(id, name, isVip);
        this.address = address;
    }
}
