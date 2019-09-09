package priv.wzb.javabase.io.cio;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/9/6 13:42
 * @description:
 */
public class CIOTest04 {
    public static void main(String[] args) throws IOException {
        String msg = FileUtils.readFileToString(new File("print.txt"),"UTF-8");
        System.out.println(msg);

        List<String> list = FileUtils.readLines(new File("print.txt"), "utf-8");
        for(String s:list){
            System.out.println(s);
        }
    }
}
