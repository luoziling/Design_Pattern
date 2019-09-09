package priv.wzb.design_pattern.structural_pattern.proxypattern.classic;

/**
 * @author Satsuki
 * @time 2019/5/19 14:29
 * @description:
 */
public class Proxy extends Subject {
    //维持一个对真实主题对象的引用
    private RealSubject realSubject = new RealSubject();

    public void PreRequest(){

    }

    @Override
    public void Request() {
        PreRequest();
        realSubject.Request();
        PostRequest();
    }
    public void PostRequest(){

    }
}
