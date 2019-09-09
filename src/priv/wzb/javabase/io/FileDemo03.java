package priv.wzb.javabase.io;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/9/4 16:43
 * @description:
 */
public class FileDemo03 {
    public static void main(String[] args) {
        String path="G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io/img/chiya.jpg";

        //绝对路径
        File src = new File(path);
        System.out.println(src.getAbsolutePath());

        //相对路径
        System.out.println(System.getProperty("user.dir"));
        src = new File("src\\priv\\wzb\\javabase\\io\\img\\chiya.jpg");
        System.out.println(src.getAbsolutePath());

        //构建一个不存在的文件
        src = new File("src\\priv\\wzb\\javabase\\io\\aaa\\chiya.jpg");


    }
}
