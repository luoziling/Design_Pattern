package priv.wzb.javabase.server.Server01;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/10 22:06
 * @description:
 * 使用ServerSocket建立与浏览器的连接 获取请求协议
 */
public class Server01 {

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server01 server = new Server01();
        server.start();
    }

    //start service
    public void start(){
        try {
            serverSocket = new ServerSocket(8888);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }

    //receive service and handle
    public void receive(){
        try {
            Socket client = serverSocket.accept();
            System.out.println("一个客户端建立了连接");
            InputStream is = client.getInputStream();
            byte[] datas = new byte[1024*1024];
            int len = is.read(datas);
            String requestInfo = new String(datas,0,len);
            System.out.println(requestInfo);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端错误");
        }
    }

    //stop service
    public void stop(){

    }
}
