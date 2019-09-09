package priv.wzb.javabase.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Satsuki
 * @time 2019/9/8 19:29
 * @description:
 * 网络爬虫原理 + 模拟浏览器
 */
public class SprderTest02 {
    public static void main(String[] args) throws Exception {
        //获取URL
        URL url = new URL("http://www.jd.com");
        //下载资源
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");


        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        String msg = null;
        while ((msg = br.readLine())!=null){
            System.out.println(msg);
        }

        br.close();
    }
}
