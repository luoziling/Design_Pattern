package priv.wzb.javabase.net.chat.chat03;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/9 20:41
 * @description:
 */
public class Receive implements Runnable {
    private Socket client;
    private DataInputStream dis;
    private boolean isRunning;

    public Receive(Socket client) {
        this.client = client;
        isRunning = true;
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
            release();
        }
        return datas;
    }

    private void release(){
        //          4. 释放资源
        this.isRunning = false;
        WzbUtils.clese(dis,client);
    }
    @Override
    public void run() {
        while (isRunning){
            String msg = receive();
            if(!msg.equals("")){
                System.out.println(msg);
            }
        }
    }
}
