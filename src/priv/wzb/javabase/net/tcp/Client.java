package priv.wzb.javabase.net.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/8 21:42
 * @description:
 * 创建客户端
 * 1. 建立连接：使用socket创建客户端 + 服务i其的地址和端口
 * 2. 操作：输入输出流操作
 * 3. 释放资源
 */
public class Client {
    public static void main(String[] args) throws IOException {
//          1. 建立连接：使用socket创建客户端 + 服务i其的地址和端口
        Socket client = new Socket("localhost",8888);
//          2. 操作：输入输出流操作
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        String data = "hello";
        dos.writeUTF(data);
        dos.flush();

//          3. 释放资源
        dos.close();
        client.close();
    }
}
