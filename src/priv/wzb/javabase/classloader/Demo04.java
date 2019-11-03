package priv.wzb.javabase.classloader;

/**
 * @author Satsuki
 * @time 2019/9/13 14:31
 * @description:
 * 测试简单的加密解密（取反）
 */
public class Demo04 {
    public static void main(String[] args) throws Exception{
//        int a = 3;//0000011
//        System.out.println(Integer.toBinaryString(a^0xff));

        //" java.lang.ClassFormatError: Incompatible magic value 889275713 in class file SEmp
//        String path2= "G:\\myjava\\priv\\wzb\\javabase\\javassist\\temp\\SEmp.class";
//        FileSystemClassLoader loader = new FileSystemClassLoader("G:\\myjava\\priv\\wzb\\javabase\\javassist\\temp");
//        Class<?> c = loader.loadClass("SEmp");
//        System.out.println(c);


        String path2= "G:\\myjava\\priv\\wzb\\javabase\\javassist\\temp\\SEmp.class";
        DecrptClassLoader loader = new DecrptClassLoader("G:\\myjava");
        Class<?> c = loader.loadClass("priv.wzb.javabase.javassist.SEmp1");
//        Class<?> c = loader.loadClass("SEmp");
        System.out.println(c);

    }
}
