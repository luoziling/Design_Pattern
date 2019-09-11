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
 * @time 2019/9/10 22:06
 * @description:
 * 使用ServerSocket建立与浏览器的连接 获取请求协议
 */
public class Server02 {

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
            InputStream is = client.getInputStream();
            byte[] datas = new byte[1024*1024];
            int len = is.read(datas);
            String requestInfo = new String(datas,0,len);
            System.out.println(requestInfo);

            StringBuilder content = new StringBuilder();
            content.append("<html>");
            content.append("<head>");
            content.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">");
            content.append("<title>");
            content.append("服务器响应成功");
            content.append("</title>");
            content.append("</head>");
            content.append("<body>");


            content.append("shsxt server终于回来了。。。。");
            content.append("</body>");
            content.append("</html>");
            //content.length是字符数 要把字符转成子节因为传输的是子节
            int size = content.toString().getBytes().length; //必须获取字节长度

            StringBuilder responseInfo =new StringBuilder();
            String blank =" ";
            String CRLF = "\r\n";
            //返回
            //1、响应行: HTTP/1.1 200 OK
            responseInfo.append("HTTP/1.1").append(blank);
            responseInfo.append(200).append(blank);
            responseInfo.append("OK").append(CRLF);
            //2、响应头(最后一行存在空行):
			/*
			 Date:Mon,31Dec209904:25:57GMT
			Server:shsxt Server/0.0.1;charset=GBK
			Content-type:text/html
			Content-length:39725426
			 */
            responseInfo.append("Date:").append(new Date()).append(CRLF);
            responseInfo.append("Server:").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
            responseInfo.append("Content-type:text/html").append(CRLF);
            responseInfo.append("Content-length:").append(size).append(CRLF);
            responseInfo.append(CRLF);
            //3、正文
            responseInfo.append(content.toString());

            //写出到客户端
            BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bw.write(responseInfo.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端错误");
        }
    }

    //stop service
    public void stop(){

    }
}
