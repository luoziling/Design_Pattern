package priv.wzb.javabase.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 */
public class IOTest02 {
    public static void main(String[] args) {
        File src = new File("src/priv/wzb/javabase/io/img/abc.txt");

        String path = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\abc.txt";
//        src = new File(path);
        System.out.println(src.getPath());
        InputStream is=null;
        try {
            is = new FileInputStream(src);

            int temp;
            while ((temp=is.read())!=-1){
                System.out.println((char)temp);
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
