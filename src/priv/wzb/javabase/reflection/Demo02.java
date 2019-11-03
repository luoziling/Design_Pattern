package priv.wzb.javabase.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Satsuki
 * @time 2019/9/12 15:37
 * @description:
 * 应用反射API动态的操作
 * 类名，属性，方法，构造器等
 */
public class Demo02 {
    public static void main(String[] args) {
        String path = "priv.wzb.javabase.reflection.RUser";
        try {
            Class<RUser> clazz = (Class<RUser>) Class.forName(path);

            //通过反射API调用构造方法，构造对象
            RUser u =  clazz.newInstance();//其实是调用了user的无参构造方法

            //记得重写类的无参构造器因为反射的应用会调用类的无参构造器生成对象
            Constructor<RUser> declaredConstructor = clazz.getDeclaredConstructor(int.class, int.class, String.class);
            RUser u2 = declaredConstructor.newInstance(1001, 17, "satsuki");
            System.out.println(u2.toString());

            //通过反射API调用普通方法
            RUser u3 = clazz.newInstance();
            System.out.println(u.hashCode()==u3.hashCode());
            Method setUname = clazz.getDeclaredMethod("setUname", String.class);
            setUname.invoke(u3,"stk3");
            System.out.println(u3.getUname());

            //通过反射API操作属性
            RUser u4 = clazz.newInstance();
            Field f = clazz.getDeclaredField("uname");
            f.setAccessible(true);//这个属性不用做安全检查直接访问
            f.set(u4,"stk4");
            System.out.println(u4.getUname());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
