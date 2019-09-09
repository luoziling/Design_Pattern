package priv.wzb.javabase.io;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/9/4 16:43
 * @description:
 */
public class FileDemo04 {
    public static void main(String[] args) {
        String path="G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io/img/chiya.jpg";

        //绝对路径
        File src = new File(path);
        System.out.println("exists?" + src.exists());

        System.out.println("file?"+ src.isFile());

        System.out.println("directory?"+src.isDirectory());

    }
}
