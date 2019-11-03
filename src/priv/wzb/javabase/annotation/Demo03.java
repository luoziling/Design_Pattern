package priv.wzb.javabase.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Satsuki
 * @time 2019/9/12 14:56
 * @description:
 * 使用Reflect读取注解的信息 模拟处理注解信息的流程
 */
public class Demo03 {
    public static void main(String[] args) {
        try {
            Class c = Class.forName("priv.wzb.javabase.annotation.WzbStudent");
            //获得类的所有注解
            Annotation[] annotations = c.getAnnotations();
            for (Annotation a : annotations){
                System.out.println(a);
            }

            //获得类指定的注解
            WzbTable wt = (WzbTable) c.getAnnotation(WzbTable.class);
            System.out.println(wt.value());

            //obtain class attribute annotation
            try {
                Field studentName = c.getDeclaredField("studentName");
                WzbField f = studentName.getAnnotation(WzbField.class);
                System.out.println(f.columnName()+"--"+f.type()+"--"+f.length());

                //根据获得表名字段的信息获取sql语句 DDL（Data Definition Language）语句
                //然后使用JDBC执行 在数据库中生成相关的表


            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
