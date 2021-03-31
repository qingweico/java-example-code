package onjava8;


import java.text.SimpleDateFormat;
import java.util.Date;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/2/23
 */
@FunctionalInterface
public interface LoginService {
    void login(UserService userService);

    default boolean isVIP() {
        return false;
    }

    // You can define methods in Object
    @Override
    String toString();

}

class UserService {
    private static User user;

    static {
        user = new User();
        user.setUsername("root");
        user.setVIP(true);
    }


    public static void login(UserService userService) {
        print("[" + userService.getUser().getUsername() + "]INFO: "
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss")
                .format(new Date()) + " " + "is login...");
    }

    public boolean isVIP(UserService userService) {
        return userService.getUser().isVIP();
    }

    public User getUser() {
        return user;
    }


    public static void main(String[] args) {


        // Static method introduction
        LoginService staticIntro = UserService::login;
        staticIntro.login(new UserService());

        LoginService anonymousInnerClass = new LoginService() {
            @Override
            public void login(UserService userService) {
                UserService.login(userService);
            }
        };
        anonymousInnerClass.login(new UserService());

        LoginService lambda = (t) -> UserService.login(t);
        lambda.login(new UserService());

        print("--------------------");

    }


}
/*Function interfaces that come with the JDK:
    java.lang.Runnable
    java.util.concurrent.Callable
    java.security.PrivilegedAction
    java.util.Comparator
    java.io.FileFilter
    java.nio.file.PathMatcher
    java.lang.reflect.InvocationHandler
    java.beans.PropertyChangeListener
*/
