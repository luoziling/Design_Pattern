package priv.wzb.javabase.net.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * @author Satsuki
 * @time 2019/9/8 20:26
 * @description:
 */
public class TalkSend implements Runnable {
    private DatagramSocket client ;
    private BufferedReader reader;
    private String toIP;
    private int toPort;

    public TalkSend(int port,String toIP,int toPort) {
        this.toIP = toIP;
        this.toPort = toPort;
        try {
            this.client = new DatagramSocket(port);
            reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            String data = null;
            try {
                data = reader.readLine();
                byte[] datas = data.getBytes();
                System.out.println("datas + "+ datas.length);
//                *  * 封装成DatagramPacket包裹 需要指定目的地
                DatagramPacket packet = new DatagramPacket(datas,0,datas.length,
                        new InetSocketAddress(toIP,toPort));
// *  * 发送包裹 send(DatagramPacket d)
                client.send(packet);
                if (data.equals("bye")){
                    System.out.println("equal");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
//                client.close();
            }

        }

        client.close();
    }
}
