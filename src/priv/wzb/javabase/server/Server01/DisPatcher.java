package priv.wzb.javabase.server.Server01;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Satsuki
 * @time 2019/9/11 15:44
 * @description:
 */
public class DisPatcher implements Runnable {

    private Socket client;
    private Request2 request;
    private Response response;

    public DisPatcher(Socket client) {
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
            if(null!=servlet){
                servlet.service(request,response);
                response.pushToBrower(200);
            }else {
                //error...
                response.pushToBrower(404);
            }
        } catch (IOException e) {
            try {
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
