package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 14:24
 * @description:
 * 理解操作步骤
 * 文件字符输入流
 */
public class Copy {
    public static void main(String[] args) {
        String path1 = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\chiya.jpg";
        String path2 = "G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\img\\copy02.jpg";
        long t1 = System.currentTimeMillis();
//        copy2("G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\io\\Copy.java","src/priv/wzb/javabase/io/img/dest.txt");
        copy2(path1,path2);
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
    }

    public static void copy(String srcPath,String destPath){
        File dest = new File(destPath);

        File src = new File(srcPath);

//        src = new File(path);
        System.out.println(src.getPath());

        InputStream is = null;
        OutputStream os = null;

        try{
            is = new FileInputStream(src);
            os = new FileOutputStream(dest,true);

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

    public static void copy2(String srcPath,String destPath){
        File dest = new File(destPath);

        File src = new File(srcPath);

//        src = new File(path);
        System.out.println(src.getPath());


        InputStream is = null;
        OutputStream os=null;
        try{
            is = new BufferedInputStream(new FileInputStream(src));
            os = new BufferedOutputStream(new FileOutputStream(dest,true));
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
}
