package priv.wzb.javabase.io;

import java.io.*;
import java.net.URL;

/**
 * @author Satsuki
 * @time 2019/9/5 16:54
 * @description:
 * 转换流
 * InputStreamReader和OutputStreamWriter
 */
public class ConvertTest {
    public static void main(String[] args) {
        //操作System.in和System.out
//        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        //操作网络流 下载百度源码
        try {
//            InputStream is = new URL("http://www.baidu.com").openStream();
//            InputStreamReader is = new InputStreamReader(new URL("http://www.baidu.com").openStream(),"utf-8");
            BufferedReader reader1 = new BufferedReader(
                    new InputStreamReader(
                            new URL("http://www.baidu.com").openStream(),"utf-8"));

//            int temp;
            String msg;
            while ((msg = reader1.readLine())!=null){
                System.out.println(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
