package priv.wzb.javabase.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 */
public class IOTest01 {
    public static void main(String[] args) {
        File src = new File("src/priv/wzb/javabase/io/img/abc.txt");

        String path = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\abc.txt";
//        src = new File(path);
        System.out.println(src.getPath());
        try {
            InputStream is = new FileInputStream(src);

            int data1 = is.read();
            int data2 = is.read();

            System.out.println((char) data1);
            System.out.println((char) data2);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }
}
