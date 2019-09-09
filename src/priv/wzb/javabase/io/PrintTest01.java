package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 17:36
 * @description:
 */
public class PrintTest01 {
    public static void main(String[] args) throws FileNotFoundException {
        //打印流
        PrintStream ps = System.out;
        ps.print("打印流");
        ps.print(true);

        ps = new PrintStream(new BufferedOutputStream(new FileOutputStream("print.txt")),true);
        ps.println("打印流");
        ps.println(true);
        ps.flush();
        //重定向输出端
        System.setOut(ps);
        System.out.println("change");
        //重定向回控制台
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out)),true));
        System.out.println("i am backing...");
    }
}
