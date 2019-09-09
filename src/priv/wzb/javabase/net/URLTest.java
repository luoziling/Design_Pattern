package priv.wzb.javabase.net;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Satsuki
 * @time 2019/9/8 19:21
 * @description:
 * URL:统一资源定位器，（互联网三大基石之一 ） （http html），区分资源
 * 1.协议
 * 2.域名或计算机名
 * 3.端口 默认80
 * 4.请求资源
 *
 */
public class URLTest {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://www.baidu.com:80/index?uname=shsxt&age=18#a");
        //获取四个值
        System.out.println("协议:"+url.getProtocol());
        System.out.println("域名|ip:"+url.getHost());
        System.out.println("port：" + url.getPort());
        System.out.println("请求资源：" + url.getFile());
        System.out.println("请求资源：" + url.getPath());
        //参数
        System.out.println("参数：" + url.getQuery());
        //锚点
        System.out.println("锚点：" + url.getRef());
    }
}
