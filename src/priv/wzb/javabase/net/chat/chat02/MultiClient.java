package priv.wzb.javabase.net.chat.chat02;

import java.io.*;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/8 22:56
 * @description:
 * 在线聊天室：客户端
 */
public class MultiClient {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost",8888);

//        Client send Message
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String msg = "";

        boolean isRunning = true;
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());

        while (isRunning){
            msg = console.readLine();

            dos.writeUTF(msg);
            dos.flush();
            //receive message
            String datas = dis.readUTF();
            System.out.println(datas);

        }



        dos.close();
        dis.close();
        client.close();

    }
}
