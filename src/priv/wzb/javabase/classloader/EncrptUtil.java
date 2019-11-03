package priv.wzb.javabase.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Satsuki
 * @time 2019/9/13 14:34
 * @description:
 * 加密工具类
 */
public class EncrptUtil {

    public static void main(String[] args) {
        String path1= "G:\\myjava\\priv\\wzb\\javabase\\javassist\\SEmp.class";
        String path2= "G:\\myjava\\priv\\wzb\\javabase\\javassist\\temp\\SEmp.class";
        encrpt(new File(path1),new File(path2));
    }

    public static void encrpt(File src,File destination){
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(destination);

            int temp = -1;
            while ((temp = fis.read())!=-1){
                //取反加密
                fos.write(temp^0xff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
