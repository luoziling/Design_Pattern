package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 * 子节舒服输出流 ByteArrayOutputStream()
 */
public class IOTest08 {
    public static void main(String[] args) {

        byte[] dest = null;


        ByteArrayOutputStream baos = null;

        try{
            baos = new ByteArrayOutputStream();

            String msg = "show me the code";
            //编码
            byte[] datas = msg.getBytes();

            baos.write(datas,0,datas.length);

            //获取数据
            dest = baos.toByteArray();
            System.out.println(new String(dest,0,baos.size()));
            baos.flush();
        }catch (Exception e){

        }finally {
            try {
                if(baos!=null){
                    baos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
