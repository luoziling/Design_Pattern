package priv.wzb.javabase.classloader;

/**
 * @author Satsuki
 * @time 2019/9/13 13:40
 * @description:
 * 类加载器的核心机制
 * 采用代理模式
 * 双亲委托机制
 * 将要加载的资源不停的交给自己的父类进行加载
 * 起到很好的保护作用
 * 例如自己定义一个java.lang.String通过类加载机制
 * 会一直加载核心库中的java.lang.String自己定义的永远不会被加载
 * tomcat服务器则与这个顺序相反
 * 也采用代理模式但是会优先考虑当前的加载器是否可以加载再往上
 */
public class Demo02 {
    public static void main(String[] args) {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(System.getProperty("java.class.path"));

        String a = "adasf";
        System.out.println(a.getClass().getClassLoader());
        System.out.println(a);

        /**
         * new String 与 = “”是一致的
         */
        String s = new String("123");
    }
}
