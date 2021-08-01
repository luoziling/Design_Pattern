package priv.wzb.netty;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * BIOTest
 *
 * @author yuzuki
 * @date 2021/7/31 13:38
 * @description:
 * 使用BIO编写服务器端监听6666端口，客户端连接开启线程通讯，线程池管理线程
 * @since 1.0.0
 */

public class BIOTest {
    @Test
    public void testThreadPool(){
//        Executors.newCachedThreadPool()
        // 无限循环使用线程池执行方法没有OOM
        Executor executor = new ThreadPoolExecutor(1,3,60, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
        while (true){
            executor.execute(new TestRunnable());
        }

    }

    class TestRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println("TestRunnable.run");
        }
    }

    @Test
    public void testCpuCores(){
        System.out.println("Runtime.getRuntime().availableProcessors() = " + Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void testFinal(){
        final int[] a = {1,2};
        a[1]= 20;
        for (int i : a) {
            System.out.println("i = " + i);
        }
        System.out.println("a = " + a);
    }

    @Test
    public void myServer() throws Exception{
        Executor executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true){
            // 阻塞
            Socket socket = serverSocket.accept();
            executor.execute(()->{
                socketHandler(socket);
            });

        }
    }

    public void socketHandler(Socket socket) {
        try {
            System.out.println("Thread:"+Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            // 阻塞
            while (inputStream.read(buffer) != -1){
                System.out.println("new String(buffer,0,buffer.length) = " + new String(buffer, 0, buffer.length,"utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
