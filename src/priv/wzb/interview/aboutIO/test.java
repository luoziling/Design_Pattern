package priv.wzb.interview.aboutIO;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/7/6 12:17
 * @description:
 */
public class test {
    public static void main(String[] args) {
        String str = "aa";
        writeFile(str);
    }

    public static void writeFile(String str){
        File file = new File("G:\\java_project_idea\\Design_Pattern\\txt\\test.txt");
        PrintStream ps = null;
        try {
            OutputStream fos = new FileOutputStream(file);
            ps = new PrintStream(fos);
            ps.print(str);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            ps.close();
        }
    }
}
