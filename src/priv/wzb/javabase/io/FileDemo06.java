package priv.wzb.javabase.io;

import java.io.File;
import java.io.IOException;

/**
 * @author Satsuki
 * @time 2019/9/4 16:43
 * @description:
 */
public class FileDemo06 {
    public static void main(String[] args) throws IOException {
        String path="G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io/img/io.txt";

        //绝对路径
        File src = new File(path);

        boolean flag = src.createNewFile();

        System.out.println(flag);

        System.out.println("length:" + src.length());



    }
}
