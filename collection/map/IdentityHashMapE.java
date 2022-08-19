package collection.map;

import lombok.extern.slf4j.Slf4j;
import object.entity.User;
import util.ObjectFactory;

import java.util.IdentityHashMap;

/**
 * {@link IdentityHashMap}
 * {@link System#identityHashCode(Object)}
 *
 * @author zqw
 * @date 2022/6/25
 */
@Slf4j
class IdentityHashMapE {
    public static void main(String[] args) {
        User u1 = ObjectFactory.create(User.class, true);
        log.info("u1 hashCode: {}", u1.hashCode());
        log.info("u1 identityHashCode: {}", System.identityHashCode(u1));
        // Object::hashCode 和 System.identityHashCode() 不一致
        // 因为无论是User::hashCode还是String::hashCode都重写了Object::hashCode
        // 而System.identityHashCode() 无论都会返回Object::hashCode
        log.info("u1 username hashCode: {}", u1.getUsername().hashCode());
        log.info("u1 username identityHashCode: {}", System.identityHashCode(u1.getUsername()));

        // User Map
        IdentityHashMap<User, String> userMap = new IdentityHashMap<>();
        User u2 = new User(1L);
        User u3 = new User(1L);
        userMap.put(u2, "u2");
        userMap.put(u3, "u3");
        log.info("userMap size: {}", userMap.size());
        // null
        log.info("userMap.get(new User(1)): {}", userMap.get(new User(1L)));
        // u2
        // IdentityHashMap 使用 == 比较key值是否相等 即key的Object::hashCode
        // 无论key是否重写了Object::hashCode 所以IdentityHashMap根据
        // System#identityHashCode(Object) 判断key是否相等
        log.info("userMap.get(u2): {}", userMap.get(u2));

        // String Map
        IdentityHashMap<String, String> stringMap = new IdentityHashMap<>();
        String s1 = new String("k_s1");
        String s2 = new String("k_s2");
        stringMap.put(s1, "v_s1");
        stringMap.put(s2, "v_s2");
        log.info("stringMap size: {}", stringMap.size());
        // null
        log.info("stringMap.get(\"k_s1\"): {}", stringMap.get("k_s1"));
        // v_s1
        log.info("stringMap.get(s1): {}", stringMap.get(s1));
    }
}
