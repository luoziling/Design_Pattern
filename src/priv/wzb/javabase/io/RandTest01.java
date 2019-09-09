package priv.wzb.javabase.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Satsuki
 * @time 2019/9/5 17:44
 * @description:
 * 随机读取和写入流RandomAccessFile
 */
public class RandTest01 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(new File("print.txt"),"r");
        //随机读取
        raf.seek(2);
        //读取
        byte[] flush = new byte[1024];
        int len=-1;
        while ((len=raf.read(flush))!=-1){
            System.out.println(new String(flush,0,len));
        }
        raf.close();
    }
}
