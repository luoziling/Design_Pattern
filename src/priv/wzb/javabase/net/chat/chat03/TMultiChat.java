package priv.wzb.javabase.net.chat.chat03;

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
public class TMultiChat {
    public static void main(String[] args) throws IOException {
        System.out.println("----Server----");

        ServerSocket server = new ServerSocket(8888);

        while (true){
            Socket client = server.accept();
            System.out.println("one client connected");
            new Thread(new Channel(client)).start();
        }






    }

    static class Channel implements Runnable{

        private Socket client;
        //输入流
        private DataInputStream dis;
        //输出流
        private DataOutputStream dos;

        private boolean isRunning;

        public Channel(Socket client) {
            this.client = client;
            try {
                //输入
                dis = new DataInputStream(client.getInputStream());

                //输出
                dos = new DataOutputStream(client.getOutputStream());

                isRunning = true;
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
            while (isRunning){
                String msg = receive();
                if(msg!=""){
                    send(msg);
                }
            }
        }

        //接收消息
        //send message
        //release source
        private String receive(){
            String datas = null;
            try {
                datas = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                release();
            }
            return datas;
        }

        private void send(String msg){
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                release();
            }
        }

        private void release(){
            //          4. 释放资源
            this.isRunning = false;
            WzbUtils.clese(dis,dos,client);
        }
    }
}


