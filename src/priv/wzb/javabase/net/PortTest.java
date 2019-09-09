package priv.wzb.javabase.net;


import java.net.InetSocketAddress;

/**
 * @author Satsuki
 * @time 2019/9/8 19:08
 * @description:
 * 端口
 * 区分软件
 * 2个子节 0  -  65535 UDP TCP
 * 同一个协议端口不能冲突
 * 定义端口越大越好
 */
public class PortTest {
    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8080);
        InetSocketAddress socketAddress2 = new InetSocketAddress("localhost", 9000);
        System.out.println(socketAddress.getHostName());
        System.out.println(socketAddress2.getAddress());
    }
}
