package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 * 文件子节输出步骤
 */
public class BufferedTest02 {
    public static void main(String[] args) {
        File src = new File("src/priv/wzb/javabase/io/img/dest.txt");

        String path = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\abc.txt";
//        src = new File(path);
        System.out.println(src.getPath());

        OutputStream os = null;

        try{
            os = new BufferedOutputStream(new FileOutputStream(src,true));

            String msg = "IO is so easy";
            //编码
            byte[] datas = msg.getBytes();

            os.write(datas,0,datas.length);

            os.flush();
        }catch (Exception e){

        }finally {
            try {
                if(os!=null){
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
