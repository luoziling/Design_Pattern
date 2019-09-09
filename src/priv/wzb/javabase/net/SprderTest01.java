package priv.wzb.javabase.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Satsuki
 * @time 2019/9/8 19:29
 * @description:
 * 网络爬虫原理
 */
public class SprderTest01 {
    public static void main(String[] args) throws Exception {
        //获取URL
        URL url = new URL("http://www.jd.com");
        //下载资源
        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
        String msg = null;
        while ((msg = br.readLine())!=null){
            System.out.println(msg);
        }

        br.close();
    }
}
