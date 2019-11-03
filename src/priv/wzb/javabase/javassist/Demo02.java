package priv.wzb.javabase.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/9/12 17:44
 * @description:
 */
public class Demo02 {
    /**
     * 处理类的基本用法
     */
    public static void test01() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("priv.wzb.javabase.javassist.SEmp");

        byte[] bytes = cc.toBytecode();
        System.out.println(Arrays.toString(bytes));
//        System.out.println(new String(bytes));
        //获取类名
        System.out.println(cc.getName());
        System.out.println(cc.getSimpleName());
        //获得父类和接口
        System.out.println(cc.getSuperclass());
        System.out.println(cc.getInterfaces());




    }

    public static void main(String[] args) throws Exception {
        test01();
    }
}
