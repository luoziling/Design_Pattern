package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 子节数组输入流
 */
public class IOTest07 {
    public static void main(String[] args) {
//        File src = new File("src/priv/wzb/javabase/io/img/abc.txt");

        byte[] src = "talk is cheap show me the code".getBytes();

        InputStream is=null;
        try {
            is = new ByteArrayInputStream(src);

            byte[] flush = new byte[5];
            int len = -1;//接受长度
            while ((len=is.read(flush))!=-1){
                //解码
//                String str = new String(car,0,3);
//                String str = new String(car);
                String str = new String(flush,0,len);
                System.out.println(str);
            }

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
