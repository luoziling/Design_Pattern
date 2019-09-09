package priv.wzb.javabase.io;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/9/4 17:10
 * @description:
 */
public class DirDemo03 {
    public static void main(String[] args) {
        File dir = new File("G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io");
        count(dir);
//        boolean flag = dir.mkdirs();
//        System.out.println(flag);
        System.out.println(len);

    }

    private static long len=0;
    public static void count(File src){
        if(src!=null && src.exists()){
            if (src.isFile()){
                //大小
                len+=src.length();
            }else {
                //子孙
                for (File s:src.listFiles()){
                    count(s);
                }
            }
        }

    }
}
