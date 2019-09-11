package priv.wzb.javabase.server.Server01;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/11 15:44
 * @description:
 * 加入状态信息处理
 */
public class DisPatcher1 implements Runnable {

    private Socket client;
    private Request2 request;
    private Response response;

    public DisPatcher1(Socket client) {
        this.client = client;
        try {
            //获取请求和响应
            request =new Request2(client);
            response =new Response(client);
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }

    }

    @Override
    public void run() {



        Servlet servlet = WebApp.getServletFromUrl(request.getUrl());

        try {

            if (null==request.getUrl()||request.getUrl().equals("")){
//                InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\server\\Server01\\index.html");
//                InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
//                System.out.println(is.read());
                byte[] bytes = new byte[1024];

                InputStream is2 = new FileInputStream("G:\\java_project_idea\\Design_Pattern\\src\\index.html");

                is2.read(bytes);
                is2.close();

                response.print(new String(bytes));
            }
            if(null!=servlet){
                servlet.service(request,response);
                response.pushToBrower(200);
            }else {
                //error...
//                InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("priv/wzb/javabase/server/Server01/error.html");
//                byte[] bytes = new byte[1024];
//                is.read(bytes);
//                is.close();
//                response.println(new String(bytes));
                byte[] bytes = new byte[1024];

                InputStream is2 = new FileInputStream("G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\server\\Server01\\error.html");

                is2.read(bytes);
                is2.close();

                System.out.println("1111111111111111111111");
                System.out.println(new String(bytes));

                response.print(new String(bytes));
                response.pushToBrower(404);
            }
        } catch (IOException e) {
            try {
                response.println("服务器出错");
                response.pushToBrower(500);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        this.release();
    }
    public void release(){
        try {
            client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
