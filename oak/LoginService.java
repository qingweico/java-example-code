package oak;


import java.text.SimpleDateFormat;
import java.util.Date;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/2/23
 */
@FunctionalInterface
public interface LoginService {
    void login(UserService userService);

    default boolean isVIP(UserService userService) {
        return false;
    }

    // You can define methods in Object
    @Override
    String toString();

}

class UserService {
    private static final User user;

    static {
        user = new User();
        user.setUsername("root");
        user.setVip(true);
    }

    public static void login() {
        System.out.println("[UserService::login]");
    }

    public static void isVIP() {
        System.out.println("[UserService::isVIP]");
    }


    public User getUser() {
        return user;
    }


    public static void main(String[] args) {


        // Static method reference >> UserService::login or UserService::isVIP
        LoginService loginService = new LoginService() {
            @Override
            public void login(UserService userService) {
                UserService.login();
            }

            @Override
            public boolean isVIP(UserService userService) {
                UserService.isVIP();
                return LoginService.super.isVIP(userService);
            }
        };
        UserService userService = new UserService();
        loginService.login(userService);

        // Anonymous Inner Class
        LoginService ls = new LoginService() {
            @Override
            public void login(UserService userService) {
                print("[" + userService.getUser().getUsername() + "]INFO: "
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new Date()) + " " + "is login...");
            }

            @Override
            public boolean isVIP(UserService userService) {
                return userService.getUser().isVip();
            }
        };

        ls.login(userService);
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
