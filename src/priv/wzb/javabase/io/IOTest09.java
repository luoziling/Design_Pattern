package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 * 图片读取到子节数组
 * 字节数组写出到文件
 */
public class IOTest09 {
    public static void main(String[] args) {
        //图片转成子节数组
        byte[] datas = fileToByteArray("G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\chiya.jpg");
        System.out.println(datas.length);
        byteArrayToFile(datas,"G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\chiya01.jpg");


    }
    public static byte[] fileToByteArray(String filePath){
        File src = new File(filePath);
        byte[] dest = null;

        InputStream is=null;
        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(src);
            baos = new ByteArrayOutputStream();

            byte[] flush = new byte[1024*10];
            int len = -1;//接受长度
            while ((len=is.read(flush))!=-1){
                //解码
//                String str = new String(car,0,3);
//                String str = new String(car);
                baos.write(flush,0,len);
            }
            baos.flush();


            is.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void byteArrayToFile(byte[] src,String filePath){

        InputStream is = null;

        File dest = new File(filePath);

        OutputStream os = null;


        try{
            is = new ByteArrayInputStream(src);
            os = new FileOutputStream(dest,true);

            byte[] flush = new byte[1024];
            int len = -1;
            while ((len=is.read(flush))!=-1){
                os.write(flush,0,len);
            }

            os.flush();
        }catch (Exception e){
            e.printStackTrace();
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
