package priv.wzb.javabase.io;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/9/4 17:10
 * @description:
 */
public class DirDemo02 {
    public static void main(String[] args) {
        File dir = new File("G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io/img/a/b");
        boolean flag = dir.mkdirs();
        System.out.println(flag);
    }
}
