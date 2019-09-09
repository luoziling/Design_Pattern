package priv.wzb.javabase.io.cio;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/9/6 13:42
 * @description:
 */
public class CIOTest03 {
    public static void main(String[] args) throws IOException {
//        String msg = FileUtils.readFileToString(new File("print.txt"),"UTF-8");
        FileUtils.write(new File("happy.txt"),"learn 学习","utf-8",true);
    }
}
