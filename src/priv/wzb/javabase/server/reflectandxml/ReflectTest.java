package priv.wzb.javabase.server.reflectandxml;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Satsuki
 * @time 2019/9/10 19:43
 * @description:
 * 反射:把java类中的各种结构(方法、属性、构造器、类名）映射成一个个的Java对象
 * 1.获取class对象
 * 三种方式:Class.forName("完整路径")
 * 2.动态创建对象
 * clz.getConstructor().newInstance();
 */
public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //三种方式
        //1.对象.getClass()
        Class clz = new Iphone().getClass();
        //2.类.class()
        clz = Iphone.class;
        //3.Class.forName("packageName.className")
        clz = Class.forName("priv.wzb.javabase.server.reflectandxml.Iphone");

        //创建对象
//        Iphone iphone = (Iphone) clz.newInstance();
//        System.out.println(iphone);
        Iphone iphone = (Iphone) clz.getConstructor().newInstance();

        System.out.println(iphone);
    }
}
class Iphone{
    public Iphone() {
    }
}
