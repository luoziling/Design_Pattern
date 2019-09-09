package priv.wzb.javabase.io;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/9/4 16:37
 * @description:
 */
public class PathDemo01 {
    public static void main(String[] args) {
        // /\名称分隔符 separator
        String path="G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\chiya.jpg";
        System.out.println(File.separator);

        //1.
        path="G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io/img/chiya.jpg";
        //2.常量拼接
//        path="G:" + File.separator +"...";
        System.out.println(path);

    }
}
