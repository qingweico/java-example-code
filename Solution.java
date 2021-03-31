import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author:qiming
 * @date: 2021/3/27
 */
public class Solution {
    static{
        int x=5;
    }
    static int x,y;
    public static void main(String args[]){
        int i = 5;
        int j = 10;
        System.out.println(~j);

    }
    public static void myMethod(){
       Thread t = new Thread(() -> {

       });

    }
}
enum AccountType
{
    SAVING, FIXED, CURRENT;
    private AccountType()
    {
        System.out.println("It is a account type");
    }
}
class EnumOne
{
    public static void main(String[]args)
    {
        System.out.println(AccountType.FIXED);
    }
}
interface A {

}



