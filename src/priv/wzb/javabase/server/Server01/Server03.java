package priv.wzb.javabase.server.Server01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author Satsuki
 * @time 2019/9/11 13:23
 * @description:
 * 封装响应信息
 * 内容可以动态添加
 * 关注状态码 拼接响应信息
 */
public class Server03 {
    private ServerSocket serverSocket;



    public static void main(String[] args) {
        Server02 server = new Server02();
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
            Request request = new Request(client);
//            Request2 request = new Request2(client);

//            Socket client = serverSocket.accept();
//            System.out.println("一个客户端建立了连接....");
//            //获取请求协议
//            Request2 request =new Request2(client);
            System.out.println("--------------111----------------------------------------");
//            System.out.println(request.getRequestInfo());

            Response response = new Response(client);

            //关注了内容
            response.print("<html>");
            response.print("<head>");
            response.print("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">");
            response.print("<title>");
            response.print("服务器响应成功");
            response.print("</title>");
            response.print("</head>");
            response.print("<body>");


            response.print("shsxt server终于回来了。。。。");
            response.print("</body>");
            response.print("</html>");

            //状态码
            response.pushToBrower(200);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端错误");
        }
    }

    //stop service
    public void stop(){

    }
}
