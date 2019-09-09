package priv.wzb.javabase.net.chat.chat04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Satsuki
 * @time 2019/9/8 22:56
 * @description:
 * 在线聊天室：服务端
 */
public class Chat {
    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<>();
    public static void main(String[] args) throws IOException {
        System.out.println("----Server----");

        ServerSocket server = new ServerSocket(8888);

        while (true){
            Socket client = server.accept();
            System.out.println("one client connected");
            Channel c = new Channel(client);
            all.add(c);
            new Thread(c).start();
        }






    }

    static class Channel implements Runnable{

        private Socket client;
        //输入流
        private DataInputStream dis;
        //输出流
        private DataOutputStream dos;

        private boolean isRunning;

        private String name;

        public Channel(Socket client) {
            this.client = client;
            try {
                //输入
                dis = new DataInputStream(client.getInputStream());

                //输出
                dos = new DataOutputStream(client.getOutputStream());

                isRunning = true;

                this.name = receive();
                this.send("欢迎你的的到来");
                this.sendOthers(this.name+"来到了聊天室",true);
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
//                    send(msg);
                    sendOthers(msg);
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

        //群聊
        private void sendOthers(String msg){
            for(Channel other:all){
                if (other==this){
                    //自己
                    continue;
                }
                other.send(this.name+"对说有人说:"+msg);
            }
        }

        //群聊系统消息
        private void sendOthers(String msg,boolean isSys){
            for(Channel other:all){
                if (other==this){
                    //自己
                    continue;
                }
                if (isSys){

                }
                other.send(msg);
            }
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
            //退出
            all.remove(this);
            sendOthers(this.name+"离开了聊天室",true);
        }
    }
}


