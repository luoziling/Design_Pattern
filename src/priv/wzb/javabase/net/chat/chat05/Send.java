package priv.wzb.javabase.net.chat.chat05;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/9 20:41
 * @description:
 */
public class Send implements Runnable {
    private Socket client;
    //输出流
    private DataOutputStream dos;

    private BufferedReader console;

    String msg;

    private boolean isRunning;

    private String name;

    public Send(Socket client,String name) {
        console = new BufferedReader(new InputStreamReader(System.in));
//        msg = this.init();
        isRunning = true;
        this.client = client;
        this.name = name;


        try {
            dos = new DataOutputStream(client.getOutputStream());
            send(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private String init(){
////          2. 操作：输入输出流操作
//
//        String uname="";
//        String upwd = "";
//        try {
//            System.out.println("请输入用户名：");
//            uname = console.readLine();
//            System.out.println("请输入pwd：");
//            upwd = console.readLine();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "uname=" + uname+"&"+"upwd=" +upwd;
//
//
//    }

    private void send(){

        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            release();
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
        WzbUtils.clese(dos,client);
    }


    private String getStrFromConsole(){
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void run() {
        while (isRunning){
            msg = getStrFromConsole();
            if(!msg.equals("")){
                send();
            }
        }
    }
}