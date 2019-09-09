package priv.wzb.javabase.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author Satsuki
 * @time 2019/9/8 20:27
 * @description:
 */
public class TalkReceive implements Runnable{
    private DatagramSocket server;

    public TalkReceive(int port) {
        try {
            server= new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true){
            byte[] container = new byte[1024*60];
            DatagramPacket packet = new DatagramPacket(container,0,container.length);
            //                * 准备容器 封装成 DatagramPacket包裹
// * 阻塞式接受包裹 receive(DatagramPacket d)
            try {
                server.receive(packet);
                // * 分析数据
//                *  byte[] getData
// *  getLength
                byte[] data = packet.getData();
                String datas = new String(data, 0, packet.getLength());
                System.out.println("对方说:"+datas);
                System.out.println(packet.getLength());
                System.out.println(datas.equals(new String("bye")));
                System.out.println("bye".length());
                System.out.println("bye".equals("bye"));
                if (datas.equals("bye")){
                    System.out.println("equal");
                    server.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//                *
        }
    }
}
