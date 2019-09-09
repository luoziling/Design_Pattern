package priv.wzb.interview.aboutIO;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Satsuki
 * @time 2019/7/6 12:58
 * @description:下面用IO和NIO两种方式实现文件拷贝，这个题目在面试的时候是经常被问到的。
 */
public class MyUtil {
    private MyUtil(){
        throw new AssertionError();
    }

    public static void fileCopy(String source,String target) throws IOException{
        try(InputStream in = new FileInputStream(source)){
            try(OutputStream out = new FileOutputStream(target)){
                byte[] buffer = new byte[4096];
                int bytesToRead;
                while ((bytesToRead = in.read(buffer))!=-1){
                    out.write(buffer,0,bytesToRead);
                }
            }
        }
    }

    public static void fileCopyNIO(String source,String target) throws IOException{
        try(FileInputStream in = new FileInputStream(source)){
            try(FileOutputStream out = new FileOutputStream(target)){
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                while (inChannel.read(buffer)!=-1){
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }
            }
        }
    }
}
