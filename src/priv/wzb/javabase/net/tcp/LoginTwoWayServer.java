package priv.wzb.javabase.net.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/8 21:42
 * @description:
 * 模拟登陆
 * 1. 指定端口 使用ServerSocket创建服务器
 * 2. 阻塞式等待连接 accept
 * 3. 操作：输入输出流操作
 * 4. 释放资源
 */
public class LoginTwoWayServer {
    public static void main(String[] args) throws IOException {
//          1. 指定端口 使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
//          2. 阻塞式等待连接 accept
        Socket client = server.accept();
        System.out.println("一个客户端建立了连接");
//          3. 操作：输入输出流操作
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String uname="";
        String upwd = "";
        //分析
        String datas = dis.readUTF();
        String[] dataArray = datas.split("&");
        for (String info:dataArray){
            String[] userInfo = info.split("=");
            System.out.println(userInfo[0]+"--->"+userInfo[1]);
            if (userInfo[0].equals("uname")){
                System.out.println("your username:" + userInfo[1]);
                uname=userInfo[1];
            }else if(userInfo[0].equals("upwd")){
                System.out.println("your password:" + userInfo[1]);
                upwd=userInfo[1];
            }

        }

        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        if (uname.equals("satsuki")&&upwd.equals(123456)){
            dos.writeUTF("登陆成功，欢迎回来");
        }else {
            System.out.println("用户名或密码错误");
        }

//          4. 释放资源
        dis.close();
        client.close();

        server.close();
    }
}
