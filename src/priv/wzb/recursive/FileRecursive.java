package priv.wzb.recursive;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/6/19 14:53
 * @description:
 */
public class FileRecursive {
    public static void main(String[] args) {
        String path = "D:\\jre";
        test(path);
    }

    private static void test(String path){
        File f = new File(path);
        File[] fs = f.listFiles();
        if (fs == null){
            return;
        }
        for (File file : fs){
            if (file.isFile()){
                System.out.println(file.getName());
            }else {
                test(file.getPath());
            }
        }
    }
}
