package coretech2.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author zqw
 * @date 2022/8/6
 */
class InetAddressTest {
    public static void main(String[] args) throws UnknownHostException {
        // 如果需要在主机和因特网地址之间相互转换 可以使用InetAddress类
        // host 不要加协议
        // getAddress 返回ip地址的4个字节的字节数组
        InetAddress address = InetAddress.getByName("qingweico.cn");
        System.out.println(address.getHostAddress());
        address = InetAddress.getByName("blog.qingweico.cn");
        System.out.println(address.getHostAddress());
        // 返回主机名
        System.out.println(address.getHostName());
        // 当域名绑定多个ip时可以使用
        InetAddress[] ips = InetAddress.getAllByName("google.com");
        System.out.println("###### google.com ######");
        for(var ip : ips) {
            System.out.println(ip.getHostAddress());
        }
        System.out.println("##### localhost #####");
        System.out.println(InetAddress.getLocalHost());



    }
}
