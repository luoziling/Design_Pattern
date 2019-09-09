package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Satsuki
 * @time 2019/5/19 15:11
 * @description:自定义请求处理程序类
 */
public class DAOLogHandler  implements InvocationHandler {
    private Calendar calendar;
    private Object object;
    public DAOLogHandler(){

    }
    //自定义构造函数，用于注入一共需要提供代理的真实对象
    public DAOLogHandler(Object object){
        this.object = object;
    }
    //实现invoke
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeInvoke();
        Object result = method.invoke(object,args);//转发调用
        afterInvoke();
        return result;
    }

    //记录方法调用时间
    public void beforeInvoke(){
        calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String time = hour + ":" + minute + ":" + second;
        System.out.println("调用时间"+time);
    }
    public void afterInvoke(){
        System.out.println("方法调用结束");
    }
}
