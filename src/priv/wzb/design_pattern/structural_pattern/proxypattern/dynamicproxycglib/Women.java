package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxycglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author Satsuki
 * @time 2019/8/17 17:32
 * @description:
 */
public class Women {
    public static void main(String[] args) {
        //JDK动态代理
//        Mishu mishu = new Mishu();
//        Gongneng gongneng = (Gongneng)Proxy.newProxyInstance(Women.class.getClassLoader(), new Class[]{Gongneng.class}, mishu);
//        gongneng.chifan();

        //CGLIB
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Laozong.class);
        enhancer.setCallback(new Xiaomi());

        Laozong laozong = (Laozong)enhancer.create();
        laozong.chifan();
    }
}
