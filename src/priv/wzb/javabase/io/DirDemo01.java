package priv.wzb.javabase.io;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/9/4 17:10
 * @description:
 */
public class DirDemo01 {
    public static void main(String[] args) {
        File dir = new File("G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io");
        //列出下级名称
        String[] list = dir.list();
        for(String s:list){
            System.out.println(s);
        }

        //下级对象
        File[] files = dir.listFiles();
        for(File f:files){
            System.out.println(f.getAbsolutePath());
        }
    }
}
