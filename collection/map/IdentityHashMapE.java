package collection.map;

import oak.User;

import java.util.IdentityHashMap;

/**
 * {@link IdentityHashMap}
 * {@link System#identityHashCode(Object)}
 * @author zqw
 * @date 2022/6/25
 */
public class IdentityHashMapE {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("");
    }
}
