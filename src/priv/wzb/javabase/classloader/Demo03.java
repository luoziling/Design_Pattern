package priv.wzb.javabase.classloader;

/**
 * @author Satsuki
 * @time 2019/9/13 14:12
 * @description:
 * 同一个类加载器加载同一个对象
 * 同一个类被不同加载器加载jvm也认为是不同的（存在疑问我这里是相同类
 */
public class Demo03 {
    public static void main(String[] args) throws Exception {
        FileSystemClassLoader loader = new FileSystemClassLoader("G:\\myjava");
        FileSystemClassLoader loader2 = new FileSystemClassLoader("G:\\myjava");


        Class<?> c = loader.loadClass("priv.wzb.javabase.javassist.SEmp");

        Class<?> c2 = loader.loadClass("priv.wzb.javabase.javassist.SEmp");
        Class<?> c3 = loader2.loadClass("priv.wzb.javabase.javassist.SEmp");



        System.out.println(c);
        System.out.println(c.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());
        System.out.println(c.equals(c3));
    }
}
