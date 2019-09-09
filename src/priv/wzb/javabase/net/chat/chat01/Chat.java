package priv.wzb.javabase.net.chat.chat01;

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
public class Chat {
    public static void main(String[] args) throws IOException {
        System.out.println("----Server----");

        ServerSocket server = new ServerSocket(8888);

        Socket client = server.accept();
        System.out.println("one client connected");

        //receive message
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String datas = dis.readUTF();
        System.out.println(datas);

        //send message
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF(datas);
        dos.flush();

        dis.close();
        dos.close();
        client.close();
    }
}
