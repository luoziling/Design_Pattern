package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 * 文件字符
 */
public class IOTest05 {
    public static void main(String[] args) {
        File src = new File("src/priv/wzb/javabase/io/img/abc.txt");

        System.out.println(src.getPath());

        Reader reader = null;

        try{
            reader = new FileReader(src);

            char[] flush = new char[1024];

            int len = -1;
            while ((len=reader.read(flush))!=-1){
                String str = new String(flush,0,len);
                System.out.println(str);

            }

        }catch (Exception e){

        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
