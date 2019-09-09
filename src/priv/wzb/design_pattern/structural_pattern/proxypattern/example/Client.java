package priv.wzb.design_pattern.structural_pattern.proxypattern.example;

/**
 * @author Satsuki
 * @time 2019/5/19 14:55
 * @description:
 */
public class Client {
    public static void main(String[] args){
        //针对抽象编程，客户端无需分辨真实类和代理类
        Search search;
        search = new ProxySearcher();
        String result = search.doSearch("yukari","yuyuko");
    }
}
