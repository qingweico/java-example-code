package io;

import java.sql.*;
import java.util.Scanner;

import static util.Print.*;

/**
 * Use sql statement
 *
 * @author:qiming
 * @date: 2020/03/12
 */

public class Login {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/sys?useSSL=false&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "990712";


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
                case "1":
                    print("-----------------登陆界面-----------------");
                    printnb("please input you username : ");
                    String username = sc.nextLine();
                    printnb("please input you password : ");
                    String password = sc.nextLine();
                    //加载驱动
                    Class.forName(JDBC_DRIVER);
                    //建立连接
                    Connection coon = DriverManager.getConnection(DB_URL, USER, PASS);
                    //创建Statement
                    String sql = "select  * from account where username= ? and password = ?";
                    PreparedStatement statement = coon.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        print("-----开始游戏(请输入1到10之间的数字)-----");
                        String yes;
                        do {
                            //登陆成功开始游戏
                            GuessNumber.start();
                            print("Whether or not to quit? yes, please say yes or no");
                            yes = sc.nextLine();
                            if ("no".equals(yes)) {
                                break;
                            }
                        } while ("yes".equals(yes));
                    } else {
                        print("sorry your account is not exist! please register firstly.......");
                    }
                    break;
                case "2":
                    print("-----------注册界面----------");
                    print("Please enter the user name you want to register : ");
                    String registerName = sc.nextLine();
                    print("Please enter the password : ");
                    String registerPassword = sc.nextLine();
                    print("input password again : ");
                    String checkPassword = sc.nextLine();
                    LoginRegister.checkPassword(registerPassword, checkPassword);
                    String Sql = "insert into account(username, password) values ('" + registerName + "','" + registerPassword + "')";
                    Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement state = con.createStatement();
                    state.executeUpdate(Sql);
                    print("注册成功!" + "您的用户名是 " + registerName + ", 密码为 " + registerPassword);
                    break;
                case "3":
                    print("欢迎下次光临呦........");
                    exit(0);
            }
        }
    }
}
