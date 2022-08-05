package tools.lombok;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import oak.User;
import util.RandomDataGenerator;
import util.SnowflakeIdWorker;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * {@link lombok.EqualsAndHashCode}
 *
 * @author zqw
 * @date 2022/6/25
 */
@Slf4j
public class LombokCallSuper {
    public static void main(String[] args) {
        String address = RandomDataGenerator.randomAddress();
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


        String username = RandomDataGenerator.randomName();
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
