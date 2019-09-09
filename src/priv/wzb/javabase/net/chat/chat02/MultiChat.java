package priv.wzb.javabase.net.chat.chat02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/8 22:56
 * @description:
 * 在线聊天室：服务端
 */
public class MultiChat {
    public static void main(String[] args) throws IOException {
        System.out.println("----Server----");

        ServerSocket server = new ServerSocket(8888);
        DataInputStream dis = null;
        DataOutputStream dos = null;
        Socket client = null;


        while (true){
            client = server.accept();
            System.out.println("one client connected");

            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            boolean isRunning = true;

            while (isRunning){
                //receive message
                String datas = dis.readUTF();
                System.out.println(datas);

                //send message
                dos.writeUTF(datas);
                dos.flush();

            }
            dis.close();
            dos.close();
            client.close();
        }



    }
}
