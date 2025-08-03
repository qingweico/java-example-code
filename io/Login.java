package io;

import cn.qingweico.database.DatabaseHelper;

import java.sql.*;
import java.util.Scanner;

import static cn.qingweico.io.Print.*;

/**
 * Use sql statement
 *
 * @author zqw
 * @date 2020/03/12
 */

class Login {
    static Connection connection;

    static {
        connection = DatabaseHelper.getConnection();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        println("---------------------欢迎来到一刀999游戏厅---------------------");
        println("|-----------------------------------------------------------|");
        println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
        println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
        println("|+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|");
        println("|-----------------------------------------------------------|");
        while (true) {
            println("----------请选择----------");
            println("1登陆");
            println("2注册");
            println("3退出");
            println("--------------------------");
            println();
            Scanner sc = new Scanner(System.in);
            String choiceString = sc.nextLine();
            switch (choiceString) {
                case "1": {
                    println("-----------------登陆界面-----------------");
                    print("用户名: ");
                    String username = sc.nextLine();
                    print("密码: ");
                    String password = sc.nextLine();

                    if (connection == null) {
                        exit(-1);
                    }
                    // 创建Statement
                    String sql = "select * from account where username = ? and password = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        println("登录成功!");
                        println("-----开始游戏(请输入1到10之间的数字)-----");
                        String yes;
                        do {
                            // 登陆成功开始游戏
                            GuessNumber.start();
                            println("还要玩吗? 继续请输入yes, 退出请输入no");
                            yes = sc.nextLine();
                            if ("no".equals(yes)) {
                                break;
                            }
                        } while ("yes".equals(yes));
                    } else {
                        println("对不起 你的账户不存在或者密码错误! 请先注册一个或者再试试密码吧!");
                    }
                    break;
                }
                case "2": {
                    println("-----------注册界面----------");
                    println("请输入您的账户昵称: ");
                    String registerName = sc.nextLine();
                    println("请输入您的密码: ");
                    String registerPassword = sc.nextLine();
                    println("确定您的密码: ");
                    String checkPassword = sc.nextLine();
                    LoginRegister.checkPassword(registerPassword, checkPassword);
                    String sql = "insert into account(username, password) values ('" + registerName + "','" + registerPassword + "')";
                    if (connection == null) {
                        exit(-1);
                    }
                    Statement state = connection.createStatement();
                    state.executeUpdate(sql);
                    println("注册成功!" + "您的用户名是 " + registerName + ", 密码为 " + registerPassword);
                    break;
                }
                case "3": {
                    println("欢迎下次光临呦........");
                    exit(0);
                }
                default: {
                    break;
                }
            }
        }
    }
}
