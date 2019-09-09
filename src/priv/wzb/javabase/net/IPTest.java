package priv.wzb.javabase.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Satsuki
 * @time 2019/9/8 13:28
 * @description:
 * ip：定位一个节点（计算机、路由
 */
public class IPTest {
    public static void main(String[] args) throws UnknownHostException {
        //使用getLocalHost方法创建InetAddress对象
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr.getHostAddress());
        System.out.println(addr.getHostName());

        //根据域名获得InetAddress对象
        addr = InetAddress.getByName("www.google.com");
        System.out.println(addr.getHostName());
        System.out.println(addr.getHostAddress());
    }
}
