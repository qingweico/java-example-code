package io;

import java.sql.*;
import java.util.Scanner;

import static util.Print.*;

/**
 * Use sql statement
 *
 * @author zqw
 * @date 2020/03/12
 */

public class Login {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/sys?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "990712";

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        print("---------------------欢迎来到一刀999游戏厅---------------------");
        print("|-----------------------------------------------------------|");
        print("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
        print("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
        print("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
        print("|-----------------------------------------------------------|");
        while (true) {
            print("----------请选择----------");
            print("1登陆");
            print("2注册");
            print("3退出");
            print("--------------------------");
            print();
            Scanner sc = new Scanner(System.in);
            String choiceString = sc.nextLine();
            switch (choiceString) {
                case "1" -> {
                    print("-----------------登陆界面-----------------");
                    printnb("用户名: ");
                    String username = sc.nextLine();
                    printnb("密码: ");
                    String password = sc.nextLine();
                    // 加载驱动
                    Class.forName(JDBC_DRIVER);
                    // 建立连接
                    Connection coon = DriverManager.getConnection(DB_URL, USER, PASS);
                    // 创建Statement
                    String sql = "select  * from account where username = ? and password = ?";
                    PreparedStatement statement = coon.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        print("-----开始游戏(请输入1到10之间的数字)-----");
                        String yes;
                        do {
                            // 登陆成功开始游戏
                            GuessNumber.start();
                            print("还要玩吗？ 亲, 继续请输入yes, 退出请输入no");
                            yes = sc.nextLine();
                            if ("no".equals(yes)) {
                                break;
                            }
                        } while ("yes".equals(yes));
                    } else {
                        print("对不起 你的账户不存在! 请先注册一个吧!");
                    }
                }
                case "2" -> {
                    print("-----------注册界面----------");
                    print("请输入您的账户昵称: ");
                    String registerName = sc.nextLine();
                    print("请输入您的密码: ");
                    String registerPassword = sc.nextLine();
                    print("确定您的密码: ");
                    String checkPassword = sc.nextLine();
                    LoginRegister.checkPassword(registerPassword, checkPassword);
                    String sql = "insert into account(username, password) values ('" + registerName + "','" + registerPassword + "')";
                    Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement state = con.createStatement();
                    state.executeUpdate(sql);
                    print("注册成功!" + "您的用户名是 " + registerName + ", 密码为 " + registerPassword);
                }
                case "3" -> {
                    print("欢迎下次光临呦........");
                    exit(0);
                }
                default -> {
                    System.out.println("invalid choice");
                }
            }
        }
    }
}
