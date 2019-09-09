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
public class CIOTest05 {
    public static void main(String[] args) throws IOException {
        FileUtils.copyFile(new File("print.txt"),new File("copyp.txt"));
    }
}
