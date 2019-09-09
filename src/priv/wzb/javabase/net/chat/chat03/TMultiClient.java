package priv.wzb.javabase.net.chat.chat03;

import java.io.*;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/8 22:56
 * @description:
 * 在线聊天室：客户端
 */
public class TMultiClient {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost",8888);

//        Client send Message

        new Thread(new Send(client)).start();

        new Thread(new Receive(client)).start();


//        client.close();

    }
}
