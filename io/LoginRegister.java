package io;

import java.io.*;
import java.util.Scanner;

import static util.Print.*;

/**
 * Use io stream
 *
 * @author:qiming
 * @date: 2020/04/23
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

class UserDaoImpl implements UserDao {
    private static final File userFile = new File("E://user.txt");

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
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    @Override
    public void userRegister(User user) {
        try (PrintWriter bw = new PrintWriter(new OutputStreamWriter(new FileOutputStream
                (userFile, true)))) {
            bw.println(user.getUsername() + "-----" + user.getPassword());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class GuessNumber {
    private GuessNumber() {
    }

    public static void start() {
        // Generate random numbers
        int number = (int) (Math.random() * 10) + 1;
        while (true) {
            Scanner sc = new Scanner(System.in);
            print("Please enter the data you want to guess: ");
            int guessNumber = sc.nextInt();
            if (number > guessNumber) {
                print("Guess the data " + guessNumber + " too small!");
            } else if (number < guessNumber) {
                print("Guess the data " + guessNumber + "too big!");
            } else {
                print("Congratulations on your guess!");
                break;
            }
        }
    }
}

class User {
    private String username;
    private String password;

    public User() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

public class LoginRegister {

    public static void checkPassword(String firstPassword, String secondPassword) {
        if (!firstPassword.equals(secondPassword)) {
            print("Sorry! The passwords you entered are different, please try again!");
            Scanner sc = new Scanner(System.in);
            secondPassword = sc.nextLine();
            checkPassword(firstPassword, secondPassword);
        }
    }

    public static void main(String[] args) {

        while (true) {
            // 欢迎界面
            print("********************欢迎光临********************");
            print("******************** 1 登陆 ********************");
            print("******************** 2 注册 ********************");
            print("******************** 3 退出 ********************");
            print("************************************************");
            //键盘录入数据
            Scanner sc = new Scanner(System.in);
            String choiceString = sc.nextLine();
            //多处使用
            UserDao ud = new UserDaoImpl();
            switch (choiceString) {
                case "1":
                    print("---------------登陆界面---------------");
                    printnb("请输入用户名: ");
                    String username = sc.nextLine();
                    printnb("请输入密码: ");
                    String password = sc.nextLine();
                    //调用功能
                    boolean flag = ud.isLogin(username, password);
                    if (flag) {
                        print("登陆成功!");
                        print("准备好了吗？开始玩游戏啦~~~~~!");
                        String y;
                        do {
                            // 启动游戏
                            GuessNumber.start();
                            print("您还要玩吗? 亲! 继续的话请输入yes 否则输入no");
                            y = sc.nextLine();
                            if ("no".equals(y)) {
                                break;
                            }
                        } while (y.equals("yes"));
                    } else {
                        print("未查询该账户信息,请您先注册 亲!");
                    }
                    break;
                case "2":
                    print("-------------注册界面------------");
                    printnb("请输入用户名: ");
                    String registerName = sc.nextLine();
                    printnb("请输入密码：");
                    String registerPassword = sc.nextLine();
                    printnb("请再一次输入密码: ");
                    String checkPassword = sc.nextLine();
                    checkPassword(registerPassword, checkPassword);
                    // 调用功能
                    // 把数据封装到对象中
                    User user = new User();
                    user.setUsername(registerName);
                    user.setPassword(registerPassword);
                    ud.userRegister(user);
                    print("注册成功 您的用户名为: " + user.getUsername() + "您的密码为: " + user.getPassword());
                    break;
                case "3":
                default:
                    print("谢谢光临，欢迎下次再来！");
                    exit(0);
                    break;
            }
        }
    }
}
