package priv.wzb.javabase.io;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/9/4 16:43
 * @description:
 */
public class FileDemo02 {
    public static void main(String[] args) {
        String path="G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io/img/chiya.jpg";

        //基本信息
        File src = new File(path);
        System.out.println("name:" + src.getName());
        System.out.println("path:" + src.getPath());
        System.out.println("absolute path:" + src.getAbsolutePath());
        System.out.println("parent path:"  + src.getParent());
        System.out.println("parent Object:"  + src.getParentFile().getName());



    }
}
