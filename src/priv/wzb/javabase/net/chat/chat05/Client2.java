package priv.wzb.javabase.net.chat.chat05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/8 22:56
 * @description:
 * 在线聊天室：客户端
 */
public class Client2 {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost",8888);

        System.out.println("请输入用户名：");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String name = br.readLine();

//        Client send Message

        new Thread(new Send(client,name)).start();

        new Thread(new Receive(client)).start();



//        client.close();

    }

}
