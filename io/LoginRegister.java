package io;

import lombok.extern.slf4j.Slf4j;
import object.entity.User;
import util.RandomDataGenerator;

import java.io.*;
import java.util.Scanner;

import static util.Print.*;

/**
 * Use io stream
 *
 * @author zqw
 * @date 2020/04/23
 */
interface UserDao {
    /**
     * 这是用户登陆功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆是否成功
     */
    boolean isLogin(String username, String password);

    /**
     * 这是用户注册功能
     *
     * @param user 用户对象
     */
    void userRegister(User user);
}

@Slf4j
class UserDaoImpl implements UserDao {
    static File userFile = new File("C://Java/user.txt");

    static {
        if (!userFile.exists()) {
            try {
                if (!userFile.createNewFile()) {
                    log.error("createNewFile error");
                    exit(-1);
                }
            } catch (IOException e) {
                log.error("createNewFile error, {}", e.getMessage());
                exit(-1);
            }
        }
    }

    @Override
    public boolean isLogin(String username, String password) {
        boolean flag = false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(userFile)));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("-----");
                if (data[0].equals(username) && data[1].equals(password)) {
                    flag = true;
                    break;
                }
            }
        } catch (IOException e) {
            log.error("[isLogin]io error");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("close error");
                }
            }
        }
        return flag;
    }

    @Override
    public void userRegister(User user) {
        try (PrintWriter bw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(userFile, true)))) {
            bw.println(user.getUsername() + "-----" + user.getPassword());
        } catch (IOException e) {
            log.error("[userRegister]io error");
        }
    }
}

class GuessNumber {
    private GuessNumber() {
    }

    public static void start() {
        // Generate random numbers
        int number = RandomDataGenerator.randomInt(10);
        while (true) {
            Scanner sc = new Scanner(System.in);
            println("请输入您猜测的数字: ");
            int guessNumber = sc.nextInt();
            if (number > guessNumber) {
                println("不好意思, 你猜的 " + guessNumber + " 太小了!");
            } else if (number < guessNumber) {
                println("不好意思, 你猜的 " + guessNumber + " 太大了!");
            } else {
                println("恭喜你猜中了! 本次游戏的答案是: " + number);
                break;
            }
        }
    }
}

/**
 * @author zqw
 */
class LoginRegister {

    public static void checkPassword(String firstPassword, String secondPassword) {
        if (!firstPassword.equals(secondPassword)) {
            println("两次密码不一致, 请重新输入: ");
            Scanner sc = new Scanner(System.in);
            secondPassword = sc.nextLine();
            checkPassword(firstPassword, secondPassword);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        while (true) {
            // Welcome Screen
            println("******************** 欢迎  *******************");
            println("******************** 1 登陆  *******************");
            println("******************** 2 注册 *******************");
            println("******************** 3 退出   *******************");
            println("*************************************************");
            Scanner sc = new Scanner(System.in);
            String choiceString = sc.nextLine();
            UserDao ud = new UserDaoImpl();
            switch (choiceString) {
                case "1": {
                    println("---------------登录---------------");
                    print("请输入用户名: ");
                    String username = sc.nextLine();
                    print("请输入密码: ");
                    String password = sc.nextLine();
                    //调用功能
                    boolean flag = ud.isLogin(username, password);
                    if (flag) {
                        println("登录成功!");
                        println("准备好了吗 要开始游戏了!");
                        String y;
                        do {
                            // Start the game
                            GuessNumber.start();
                            println("输入yes继续, no结束");
                            y = sc.nextLine();
                            if ("no".equals(y)) {
                                break;
                            }
                        } while ("yes".equals(y));
                    } else {
                        println("账户不存在或者密码错误, 请先注册或者再试试密码吧!");
                    }
                    break;
                }
                case "2": {
                    println("-------------注册------------");
                    print("请输入用户名: ");
                    String registerName = sc.nextLine();
                    print("请输入密码: ");
                    String registerPassword = sc.nextLine();
                    print("请再次输入密码: ");
                    String checkPassword = sc.nextLine();
                    checkPassword(registerPassword, checkPassword);
                    // Encapsulate data in objects
                    User user = new User();
                    user.setUsername(registerName);
                    user.setPassword(registerPassword);
                    ud.userRegister(user);
                    println("注册成功! 您的用户名是: " + user.getUsername() + "密码是: " + user.getPassword());
                    break;
                }
                case "3": {
                    println("欢迎下次光临呦!");
                    exit(0);
                }
                default: {
                    break;
                }
            }
        }
    }
}
