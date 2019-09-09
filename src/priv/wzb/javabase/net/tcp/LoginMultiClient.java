package priv.wzb.javabase.net.tcp;

import java.io.*;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/8 21:42
 * @description:
 * 模拟登陆
 * 1. 建立连接：使用socket创建客户端 + 服务i其的地址和端口
 * 2. 操作：输入输出流操作
 * 3. 释放资源
 */
public class LoginMultiClient {
    public static void main(String[] args) throws IOException {
//          1. 建立连接：使用socket创建客户端 + 服务i其的地址和端口
        Socket client = new Socket("localhost",8888);


        new Send(client).send();
//        dos.writeUTF("uname=" + uname+"&"+"upwd=" +upwd);
//        dos.flush();

//        DataInputStream dis = new DataInputStream(client.getInputStream());
//        String result = dis.readUTF();
        String result = new Receive(client).receive();
        System.out.println(result);
        

//          3. 释放资源
//        dos.close();
        client.close();
    }

    //发送
    static class Send{
        private Socket client;
        //输出流
        private DataOutputStream dos;

        private BufferedReader console;

        String msg;

        public Send(Socket client) {
            console = new BufferedReader(new InputStreamReader(System.in));
            msg = this.init();
            this.client = client;
            try {
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String init(){
//          2. 操作：输入输出流操作

            String uname="";
            String upwd = "";
            try {
                System.out.println("请输入用户名：");
                uname = console.readLine();
                System.out.println("请输入pwd：");
                upwd = console.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "uname=" + uname+"&"+"upwd=" +upwd;


        }

        private void send(){

            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    //接收
    static class Receive{
        private Socket client;
        private DataInputStream dis;

        public Receive(Socket client) {
            this.client = client;
            //输入
            try {
                dis = new DataInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private String receive(){
            String datas = null;
            try {
                datas = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return datas;
        }
    }
}
