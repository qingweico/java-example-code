package oak.lambda;


import object.entity.User;
import cn.qingweico.io.Print;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * --------------- Functional Interface ---------------
 *
 * @author zqw
 * @date 2021/2/23
 */
@FunctionalInterface
public interface LoginService {
    /**
     * login
     *
     * @param userService ///
     */
    void login(UserService userService);

    /**
     * user is vip
     *
     * @param userService ///
     * @return user is vip
     */
    default boolean isVip(UserService userService) {
        return false;
    }

    // You can define methods in Object

    /**
     * reload toString()
     *
     * @return reloaded toString() ///
     */
    @Override
    String toString();

}

class UserService {
    private static final User USER;

    static {
        USER = new User();
        USER.setUsername("root");
        USER.setVip(true);
    }

    public static void login() {
        System.out.println("[UserService::login]");
    }

    public static void isVip() {
        System.out.println("[UserService::isVIP]");
    }


    public User getUser() {
        return USER;
    }


    public static void main(String[] args) {


        // Static method reference >> UserService::login or UserService::isVIP
        LoginService loginService = new LoginService() {
            @Override
            public void login(UserService userService) {
                UserService.login();
            }

            @Override
            public boolean isVip(UserService userService) {
                UserService.isVip();
                return LoginService.super.isVip(userService);
            }
        };
        UserService userService = new UserService();
        loginService.login(userService);

        // Anonymous Inner Class
        LoginService ls = new LoginService() {
            @Override
            public void login(UserService userService) {
                Print.println("[" + userService.getUser().getUsername() + "]INFO: "
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new Date()) + " " + "is login...");
            }

            @Override
            public boolean isVip(UserService userService) {
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
