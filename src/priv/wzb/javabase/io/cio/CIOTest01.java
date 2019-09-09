package priv.wzb.javabase.io.cio;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.util.Collection;

/**
 * @author Satsuki
 * @time 2019/9/6 13:42
 * @description:
 */
public class CIOTest01 {
    public static void main(String[] args) {
        String path = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\cio\\CIOTest01.java";
        String directory = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase";
//        Collection<File> files = FileUtils.listFiles(new File(directory), EmptyFileFilter.NOT_EMPTY, DirectoryFileFilter.INSTANCE);
        Collection<File> files = FileUtils.listFiles(new File(directory),
                new SuffixFileFilter("java"),
                DirectoryFileFilter.INSTANCE);

        for(File file:files){
            System.out.println(file.getAbsolutePath());
        }

    }
}
