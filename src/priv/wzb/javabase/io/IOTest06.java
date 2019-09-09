package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 * 文件字符
 */
public class IOTest06 {
    public static void main(String[] args) {
        File src = new File("src/priv/wzb/javabase/io/img/abc.txt");

        System.out.println(src.getPath());

        Writer writer = null;

        try{
            writer = new FileWriter(src,true);
//            writer = new FileWriter(src);

            String msg = "今天天气真好 Satsuki";

//            char[] datas = msg.toCharArray();
//            writer.write(datas,0,datas.length);
            writer.append(msg,0,msg.length()).append("");

            writer.flush();

        }catch (Exception e){

        }finally {
            try {
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
