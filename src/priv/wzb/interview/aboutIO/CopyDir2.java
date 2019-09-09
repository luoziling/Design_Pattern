package priv.wzb.interview.aboutIO;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/7/6 13:29
 * @description:
 */
public class CopyDir2 {
    public static void main(String[] args) {
        try {
            copyDirectionry("G:\\java_project_idea\\Design_Pattern\\copytest","G:\\java_project_idea\\Design_Pattern\\copytest1");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 复制单个文件
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    private static void copyFile(File sourceFile,File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff=null;
        try{
            //新建文件输入流
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            //新建文件输出流
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            //缓冲数据
            byte[] b = new byte[1024*5];
            int len;
            while ((len = inBuff.read(b))!=-1){
                outBuff.write(b,0,len);
            }
            //刷新此缓冲的输出流
            outBuff.flush();;
        }finally {
            //关闭流
            if(inBuff != null){
                inBuff.close();
            }
            if (outBuff!=null){
                outBuff.close();
            }
        }
    }

    /**
     * 复制目录
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     */
    private static void copyDirectionry(String sourceDir,String targetDir) throws IOException{
        //检查源目录
        File fSourceDir = new File(sourceDir);
        if(!fSourceDir.exists() || !fSourceDir.isDirectory()){
            return;
        }
        //检查目标目录，如不存在则创建
        File fTargetDir = new File(targetDir);
        if(!fTargetDir.exists()){
            fTargetDir.mkdirs();
        }
        //遍历源目录下的文件或目录
        File[] files = fSourceDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()){
                //源文件
                File sourceFile = files[i];
                //目标文件
                File targetFile = new File(fTargetDir,files[i].getName());
                copyFile(sourceFile,targetFile);
            }
            //递归复制子目录
            if(files[i].isDirectory()){
                //准备源文件夹
                String subSourceDir = sourceDir + File.separator + files[i].getName();
                //准备复制的目标文件夹
                String subTargetDir = targetDir + File.separator + files[i].getName();
                //复制子目录
                copyDirectionry(subSourceDir,subTargetDir);
            }
        }
    }
}
