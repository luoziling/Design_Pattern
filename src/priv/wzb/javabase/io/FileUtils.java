package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 16:03
 * @description:
 * 1.封装拷贝
 * 2.封装释放资源
 */
public class FileUtils {
    public static void main(String[] args) {
        //文件到文件
        try {
            InputStream is = new FileInputStream("G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\abc.txt");
            OutputStream os = new FileOutputStream("G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\abc01.txt");
            copy(is,os);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //文件到字节数组
        byte[] datas=null;
        try {
            InputStream is = new FileInputStream("G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\chiya.jpg");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            copy(is,os);
            datas = os.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            InputStream is = new ByteArrayInputStream(datas);
            OutputStream os = new FileOutputStream("G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\copy01.jpg");
            copy(is,os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }

    /**
     * 对接输入输出流
     * @param is
     * @param os
     */
    public static void copy(InputStream is,OutputStream os){

        try{

            byte[] flush = new byte[1024];
            int len=-1;
            while ((len=is.read(flush))!=-1){
                os.write(flush,0,len);
            }

            os.flush();
        }catch (Exception e){

        }finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(is!=null){
                    is.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public static void close(Closeable... ios){
        for (Closeable io:ios) {
            try {
                if(io!=null){
                    io.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
