package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxycglib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Satsuki
 * @time 2019/8/17 17:29
 * @description:
 */
public class Mishu implements InvocationHandler {
    private Laozong laozong = new Laozong();
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("预约时间");
        Object result = method.invoke(laozong, args);
        System.out.println("记录信息");
        return result;
    }
}
