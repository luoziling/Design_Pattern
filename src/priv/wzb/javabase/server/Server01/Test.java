package priv.wzb.javabase.server.Server01;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Satsuki
 * @time 2019/9/11 21:23
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        FileInputStream is=(FileInputStream) Thread.currentThread().getContextClassLoader().getResourceAsStream("G:\\java_project_idea\\Design_Pattern\\src\\index.html");
        try {
            byte[] bytes = new byte[2];
//            System.out.println(is.read(bytes));

//            InputStream is1 =Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
            InputStream is2 = new FileInputStream("G:\\java_project_idea\\Design_Pattern\\src\\index.html");
            System.out.println(is2.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
