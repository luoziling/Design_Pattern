package priv.wzb.javabase.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author Satsuki
 * @time 2019/9/8 19:48
 * @description:
 * 发送端
 *  * 使用DatagramSocket 指定端口 创建接收端
 *  * 准备数据容器
 *  * 封装成DatagramPacket包裹 需要指定目的地
 *  * 发送包裹 send(DatagramPacket d)
 *  *
 *  * 释放资源
 */
public class UdpClient {
    public static void main(String[] args) throws Exception{
        System.out.println("sender starting...");
//         *  * 使用DatagramSocket 指定端口 创建接收端
        DatagramSocket client = new DatagramSocket(8888);

//                *  * 准备数据容器
        String data = "曾根美雪";
//        System.out.println(data.getBytes());
        byte[] datas = data.getBytes();
        System.out.println("datas + "+ datas.length);
//                *  * 封装成DatagramPacket包裹 需要指定目的地
        DatagramPacket packet = new DatagramPacket(datas,0,datas.length,new InetSocketAddress("localhost",9999));
// *  * 发送包裹 send(DatagramPacket d)
        client.send(packet);

        client.close();

    }
}
