package priv.wzb.javabase.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author Satsuki
 * @time 2019/9/8 19:48
 * @description:
 * 接收端
 * 使用DatagramSocket 指定端口 创建接收端
 * 准备容器 封装成 DatagramPacket包裹
 * 阻塞式接受包裹 receive(DatagramPacket d)
 * 分析数据
 *  byte[] getData
 *  getLength
 *
 * 释放资源
 */
public class UdpServer {
    public static void main(String[] args) throws Exception{
//         * 使用DatagramSocket 指定端口 创建接收端
        DatagramSocket server = new DatagramSocket(9999);
//                * 准备容器 封装成 DatagramPacket包裹
        byte[] container = new byte[1024*60];
        DatagramPacket packet = new DatagramPacket(container,0,container.length);
// * 阻塞式接受包裹 receive(DatagramPacket d)
        server.receive(packet);
// * 分析数据
//                *  byte[] getData
// *  getLength
        byte[] data = packet.getData();
        System.out.println(new String(data,0,data.length));
        System.out.println(packet.getLength());
//                *
// * 释放资源
        server.close();
    }
}
