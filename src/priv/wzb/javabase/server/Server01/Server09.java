package priv.wzb.javabase.server.Server01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标: 处理404 505和首页
 * 
 * @author 裴新 QQ:3401997271
 *
 */
public class Server09 {
	private ServerSocket serverSocket ;
	private boolean isRunning;
	public static void main(String[] args) {
		Server09 server = new Server09();
		server.start();
	}
	//启动服务
	public void start() {
		try {
			serverSocket =  new ServerSocket(8888);
			isRunning = true;
			 receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败....");
		}
	}
	//接受连接处理
	public void receive() {
		while (isRunning) {
			try {

				Socket client = serverSocket.accept();
				System.out.println("一个客户端建立了连接....");

				//多线程
				new Thread(new DisPatcher1(client)).start();






			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("客户端错误");
			}
		}
	}
	//停止服务
	public void stop() {
		isRunning = false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
