package priv.wzb.javabase.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 */
public class IOTest03 {
    public static void main(String[] args) {
        File src = new File("src/priv/wzb/javabase/io/img/abc.txt");

        String path = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\abc.txt";
//        src = new File(path);
        System.out.println(src.getPath());
        InputStream is=null;
        try {
            is = new FileInputStream(src);

            byte[] flush = new byte[1024];
            int len = -1;//接受长度
            while ((len=is.read(flush))!=-1){
                //解码
//                String str = new String(car,0,3);
//                String str = new String(car);
                String str = new String(flush,0,len);
                System.out.println(str);
            }

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
