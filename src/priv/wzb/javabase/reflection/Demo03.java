package priv.wzb.javabase.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Satsuki
 * @time 2019/9/12 15:37
 * @description:
 * 应用反射API获取类的信息（类名，属性，方法，构造器）
 */
public class Demo03 {
    public static void main(String[] args) {
        String path = "priv.wzb.javabase.reflection.RUser";
        try {
            Class clazz = Class.forName(path);
            //获取类名
            System.out.println(clazz.getName());//获得包名+类名
            System.out.println(clazz.getSimpleName());//获得类名

            //获取属性信息
            Field[] fields = clazz.getFields();//只能获得public的属性(field)
            System.out.println(fields.length);
            Field[] declaredFields = clazz.getDeclaredFields();//获得所有的属性（声明的属性
            System.out.println(declaredFields.length);
            Field uname = clazz.getDeclaredField("uname");
            for(Field tmp:declaredFields){
                System.out.println("attribute:" + tmp);
            }

            //获取方法信息
            Method[] methods = clazz.getMethods();
            System.out.println(methods.length);
            Method[] declaredMethods = clazz.getDeclaredMethods();
            Method m01 = clazz.getDeclaredMethod("getUname", null);
            //如果有参必须传递参数类型对应的class对象否则因为重载无法确定特定的方法
            Method m02 = clazz.getDeclaredMethod("setUname", String.class);
            for(Method method:declaredMethods){
                System.out.println("method:" + method);
            }



            //获取构造器信息
            Constructor[] constructors = clazz.getConstructors();
            Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
            for(Constructor constructor:constructors){
                System.out.println("constructor:" + constructor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
