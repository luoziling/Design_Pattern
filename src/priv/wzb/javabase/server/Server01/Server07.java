package priv.wzb.javabase.server.Server01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标: 整合web.xml
 * 
 * @author 裴新 QQ:3401997271
 *
 */
public class Server07 {
	private ServerSocket serverSocket ;
	public static void main(String[] args) {
		Server07 server = new Server07();
		server.start();
	}
	//启动服务
	public void start() {
		try {
			serverSocket =  new ServerSocket(8888);
			 receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败....");
		}
	}
	//接受连接处理
	public void receive() {
		try {
			Socket client = serverSocket.accept();
			System.out.println("一个客户端建立了连接....");
			//获取请求协议
			Request2 request =new Request2(client);
			
			Response response =new Response(client);

			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());

			if(null!=servlet){
				servlet.service(request,response);
				response.pushToBrower(200);
			}else {
				//error...
				response.pushToBrower(404);
			}



		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("客户端错误");
		}
	}
	//停止服务
	public void stop() {
		
	}
}
