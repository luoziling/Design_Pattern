# Net复习

## 1.定位

IP：地址

端口：对应项目

URL：资源

InetSocketAddress

```java
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Satsuki
 * @time 2019/9/8 13:28
 * @description:
 * ip：定位一个节点（计算机、路由
 */
public class IPTest {
    public static void main(String[] args) throws UnknownHostException {
        //使用getLocalHost方法创建InetAddress对象
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr.getHostAddress());
        System.out.println(addr.getHostName());

        //根据域名获得InetAddress对象
        addr = InetAddress.getByName("www.google.com");
        System.out.println(addr.getHostName());
        System.out.println(addr.getHostAddress());
    }
}
```



URL

```java
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Satsuki
 * @time 2019/9/8 19:21
 * @description:
 * URL:统一资源定位器，（互联网三大基石之一 ） （http html），区分资源
 * 1.协议
 * 2.域名或计算机名
 * 3.端口 默认80
 * 4.请求资源
 *
 */
public class URLTest {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://www.baidu.com:80/index?uname=shsxt&age=18#a");
        //获取四个值
        System.out.println("协议:"+url.getProtocol());
        System.out.println("域名|ip:"+url.getHost());
        System.out.println("port：" + url.getPort());
        System.out.println("请求资源：" + url.getFile());
        System.out.println("请求资源：" + url.getPath());
        //参数
        System.out.println("参数：" + url.getQuery());
        //锚点
        System.out.println("锚点：" + url.getRef());
    }
}
```



## 2.TCP/UDP

tcp:面向连接  安全 效率慢



Server accept

Socket IO

**虚拟聊天室：**



```java
package priv.wzb.javabase.net.chat.chat05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
            boolean isPrivate = msg.startsWith("@");
            if (isPrivate){
                int idx = msg.indexOf(":");
                //私聊
                //获取目标和数据
                String targetName = msg.substring(1,idx);
                msg = msg.substring(idx+1);
                for(Channel other:all){
                    if(other.name.equals(targetName)){
                        other.send(this.name+"悄悄地对你说:"+msg);
                    }
                }
            }else {
                for(Channel other:all){
                    if (other==this){
                        //自己
                        continue;
                    }
                    other.send(this.name+"对说有人说:"+msg);
                }
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
```



```java
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
```



```java
package priv.wzb.javabase.net.chat.chat05;

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
```

```java
package priv.wzb.javabase.net.chat.chat05;

import java.io.Closeable;

/**
 * @author Satsuki
 * @time 2019/9/9 20:32
 * @description:
 */
public class WzbUtils {
    /**
     * 释放资源
     */
    public static void clese(Closeable... targets){
        for(Closeable target:targets){
            try {
                if(target!=null){
                    target.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
```



```java
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
public class Client {
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
```

**注意：client 可以开启多个**

udp: 面向无连接  不安全 效率高

数据包为传输数据的格式

DatagramSocket : send ,receive

DatagramPacket 封包和拆包



```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * @author Satsuki
 * @time 2019/9/8 20:26
 * @description:
 */
public class TalkSend implements Runnable {
    private DatagramSocket client ;
    private BufferedReader reader;
    private String toIP;
    private int toPort;

    public TalkSend(int port,String toIP,int toPort) {
        this.toIP = toIP;
        this.toPort = toPort;
        try {
            this.client = new DatagramSocket(port);
            reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            String data = null;
            try {
                data = reader.readLine();
                byte[] datas = data.getBytes();
                System.out.println("datas + "+ datas.length);
//                *  * 封装成DatagramPacket包裹 需要指定目的地
                DatagramPacket packet = new DatagramPacket(datas,0,datas.length,
                        new InetSocketAddress(toIP,toPort));
// *  * 发送包裹 send(DatagramPacket d)
                client.send(packet);
                if (data.equals("bye")){
                    System.out.println("equal");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
//                client.close();
            }

        }

        client.close();
    }
}
```



```java
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author Satsuki
 * @time 2019/9/8 20:27
 * @description:
 */
public class TalkReceive implements Runnable{
    private DatagramSocket server;

    public TalkReceive(int port) {
        try {
            server= new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true){
            byte[] container = new byte[1024*60];
            DatagramPacket packet = new DatagramPacket(container,0,container.length);
            //                * 准备容器 封装成 DatagramPacket包裹
// * 阻塞式接受包裹 receive(DatagramPacket d)
            try {
                server.receive(packet);
                // * 分析数据
//                *  byte[] getData
// *  getLength
                byte[] data = packet.getData();
                String datas = new String(data, 0, packet.getLength());
                System.out.println("对方说:"+datas);
                System.out.println(packet.getLength());
                System.out.println(datas.equals(new String("bye")));
                System.out.println("bye".length());
                System.out.println("bye".equals("bye"));
                if (datas.equals("bye")){
                    System.out.println("equal");
                    server.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//                *
        }
    }
}
```