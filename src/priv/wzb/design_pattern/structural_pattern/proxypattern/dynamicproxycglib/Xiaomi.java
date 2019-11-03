package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxycglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Satsuki
 * @time 2019/8/17 17:40
 * @description:
 */
public class Xiaomi implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("预约时间");
//        method.invoke(o,objects);
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("备注");
        return result;
    }
}
