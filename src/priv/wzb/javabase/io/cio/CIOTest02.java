package priv.wzb.javabase.io.cio;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author Satsuki
 * @time 2019/9/6 13:42
 * @description:
 */
public class CIOTest02 {
    public static void main(String[] args) {
        String path = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\cio\\CIOTest01.java";
        String directory = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase";
        //文件大小
        long len = FileUtils.sizeOf(new File(path));
        System.out.println(len);
        //目录大小
        len = FileUtils.sizeOf(new File(directory));
        System.out.println(len);

    }
}
