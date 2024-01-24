package util.net;

import thread.aqs.ObjectPool;
import util.DatabaseHelper;
import util.Print;
import util.constants.Symbol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * IP 字符串类型和 Long 互相转换
 *
 * @author zqw
 * @date 2022/8/18
 */
public final class IpStr2LongUtil {
    private IpStr2LongUtil() {
    }

    private final static ObjectPool<Connection, Object> P = DatabaseHelper.getPool();

    public static Long ipStr2Long(String ipStr) {
        String[] ip = ipStr.split("\\.");
        return (Long.parseLong(ip[0]) << 24) +
                (Long.parseLong(ip[1]) << 16) +
                (Long.parseLong(ip[2]) << 8) +
                (Long.parseLong(ip[3]));
    }

    public static String long2IpStr(long ip) {
        return (ip >>> 24) +
                Symbol.DOT +
                ((ip >>> 16) & 0xFF) +
                Symbol.DOT +
                ((ip >>> 8) & 0xFF) +
                Symbol.DOT +
                (ip & 0xFF);
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        final Long ipStr = ipStr2Long("192.168.2.9");
        System.out.println(ipStr);
        System.out.println(long2IpStr(ipStr));
        mysql("127.0.0.1");
        mysql("192.168.2.9");
    }

    /*MySQL也提供了字符串ip与整数之间相互转换的函数*/

    private static void mysql(String ipStr) throws InterruptedException {
        /*inet_aton() 将字符串格式转换为整数格式;ipv6请使用inet6_aton()*/
        Object ret = P.submit((connection -> {
            long ip = 0;
            String sql = "select inet_aton(?)";
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, ipStr);
                ResultSet resultSet = ps.executeQuery();
                // !! un lack
                resultSet.next();
                ip = Long.parseLong(resultSet.getString(1));
                System.out.println(ip);
            } catch (SQLException e) {
                Print.err(e.getMessage());
            }
            return ip;
        }));
        /*inet_ntoa() 将整数格式转换为字符串格式;ipv6请使用inet6_ntoa()*/
        P.exec((connection -> {
            final long ip = (long) ret;
            String sql = "select inet_ntoa(?)";
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement(sql);
                ps.setLong(1, ip);
                ResultSet resultSet = ps.executeQuery();
                resultSet.next();
                System.out.println(resultSet.getString(1));
            } catch (SQLException e) {
                Print.err(e.getMessage());
            }
        }));
    }
}
