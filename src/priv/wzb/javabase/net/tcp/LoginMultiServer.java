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
public class LoginMultiServer {
    public static void main(String[] args) throws IOException {
//          1. 指定端口 使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
        boolean isRunning = true;
        while (isRunning){
            //          2. 阻塞式等待连接 accept
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接");
            new Thread(new Channel(client)).start();
        }

        server.close();

    }

//    一个Channel代表一个客户端
    static class Channel implements Runnable{
        private Socket client;
        //输入流
        private DataInputStream dis;
        //输出流
        private DataOutputStream dos;

        public Channel(Socket client) {
            this.client = client;
            try {
                //输入
                dis = new DataInputStream(client.getInputStream());

                //输出
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    client.close();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        }

        @Override
        public void run() {


//          3. 操作：输入输出流操作

            String uname="";
            String upwd = "";
            //分析
            String datas = receive();
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


            if (uname.equals("satsuki")&&upwd.equals("123456")){
                send("登陆成功，欢迎回来");
            }else {
                send("用户名或密码错误");
            }

            release();


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

        private void send(String msg){
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void release(){
            //          4. 释放资源
            try {
                if (dis!=null){
                    dis.close();
                }
                if (dos!=null) {
                    dos.close();
                }
                if (client!=null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //发送
    static class Send{
        private Socket client;
        //输出流
        private DataOutputStream dos;

        public Send(Socket client) {
            this.client = client;
            try {
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void send(String msg){

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
