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

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        while (true) {
            // Welcome Screen
            print("******************** Welcome  *******************");
            print("******************** 1 Login  *******************");
            print("******************** 2 Regist *******************");
            print("******************** 3 exit   *******************");
            print("*************************************************");
            Scanner sc = new Scanner(System.in);
            String choiceString = sc.nextLine();
            UserDao ud = new UserDaoImpl();
            switch (choiceString) {
                case "1" -> {
                    print("---------------Login---------------");
                    printnb("please enter user name: ");
                    String username = sc.nextLine();
                    printnb("Please enter the password: ");
                    String password = sc.nextLine();
                    //调用功能
                    boolean flag = ud.isLogin(username, password);
                    if (flag) {
                        print("login successfully!");
                        print("Are you ready? Let's play the game~~~~~!");
                        String y;
                        do {
                            // Start the game
                            GuessNumber.start();
                            print("Do you want to play? Dear! Please enter yes to continue, otherwise enter no");
                            y = sc.nextLine();
                            if ("no".equals(y)) {
                                break;
                            }
                        } while (y.equals("yes"));
                    } else {
                        print("The account information has not been queried, please register first, dear!");
                    }
                }
                case "2" -> {
                    print("-------------Register------------");
                    printnb("please enter user name: ");
                    String registerName = sc.nextLine();
                    printnb("Please enter the password: ");
                    String registerPassword = sc.nextLine();
                    printnb("Please enter the password again: ");
                    String checkPassword = sc.nextLine();
                    checkPassword(registerPassword, checkPassword);
                    // Encapsulate data in objects
                    User user = new User();
                    user.setUsername(registerName);
                    user.setPassword(registerPassword);
                    ud.userRegister(user);
                    print("Registered successfully. Your username is: " + user.getUsername() + "Your password is: " + user.getPassword());
                }
                default -> {
                    print("Thank you for coming, welcome to come again next time！");
                    exit(0);
                }
            }
        }
    }
}
